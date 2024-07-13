package pl.edu.wszib.car.rental.services.impl;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wszib.car.rental.dao.impl.spring.data.CarDAO;
import pl.edu.wszib.car.rental.dao.impl.spring.data.OrderDAO;
import pl.edu.wszib.car.rental.dao.impl.spring.data.UserDAO;
import pl.edu.wszib.car.rental.exceptions.EmptyCartException;
import pl.edu.wszib.car.rental.exceptions.IncorrectCartPositionsException;
import pl.edu.wszib.car.rental.exceptions.UserNotLoggedException;
import pl.edu.wszib.car.rental.model.Car;
import pl.edu.wszib.car.rental.model.Order;
import pl.edu.wszib.car.rental.model.Position;
import pl.edu.wszib.car.rental.model.User;
import pl.edu.wszib.car.rental.services.IOrderService;
import pl.edu.wszib.car.rental.session.SessionConstants;
import pl.edu.wszib.car.rental.session.SessionObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final HttpSession httpSession;
    private final CarDAO carDAO;
    private final OrderDAO orderDAO;
    private final UserDAO userDAO;

    @Resource
    private SessionObject sessionObject;

    @Override
    public void confirmOrder() {
        Set<Position> cart = (Set<Position>) this.httpSession.getAttribute(SessionConstants.CART_KEY);
        if(cart == null || cart.isEmpty()) {
            throw new EmptyCartException();
        }
        List<Position> toRemove = cart.stream().filter(p -> {
            Optional<Car> carBox = this.carDAO.findById(p.getCar().getId());
            return carBox.isEmpty();
        }).toList();

        if(!toRemove.isEmpty()) {
            toRemove.forEach(cart::remove);
            throw new IncorrectCartPositionsException();
        }

        Order order = new Order();
        order.setDate(LocalDateTime.now());
        order.setUser((User) this.httpSession.getAttribute(SessionConstants.USER_KEY));
        order.setStatus(Order.Status.NEW);
        order.getPositions().addAll(cart);
        order.setSum(order.getPositions().stream()
                .mapToDouble(p -> p.getCar().getPrice()).sum());

        order.getPositions().forEach(p -> {
            this.carDAO.findById(p.getCar().getId()).ifPresent(
                    car -> {
                        car.setReserved(1);
                        this.carDAO.save(car);
                    }
            );
        });

        this.orderDAO.save(order);
        cart.clear();
    }

    @Override
    public List<Order> getOrdersForCurrentUser() {
        User user = (User) this.httpSession.getAttribute(SessionConstants.USER_KEY);
        if(user == null) {
            throw new UserNotLoggedException();
        }

        return new ArrayList<>(this.userDAO.findById(user.getId()).get().getOrders());
    }

    @Override
    public List<Order> getAllOrders() {
        User user = (User) this.httpSession.getAttribute(SessionConstants.USER_KEY);
        if(user == null) {
            throw new UserNotLoggedException();
        }

        if (user.getRole() != User.Role.ADMIN) {
            return new ArrayList<>();
        }

        List<Order> orders = new ArrayList<>();
        orderDAO.findAll().forEach(orders::add);
        return orders;
    }
}
