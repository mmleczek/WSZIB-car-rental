package pl.edu.wszib.car.rental.services.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wszib.car.rental.dao.impl.spring.data.CarDAO;
import pl.edu.wszib.car.rental.model.Car;
import pl.edu.wszib.car.rental.model.Position;
import pl.edu.wszib.car.rental.services.ICartService;
import pl.edu.wszib.car.rental.session.SessionConstants;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CarDAO carDAO;
    private final HttpSession httpSession;


    public double calculateCartSum() {
        Set<Position> cart = (Set<Position>) this.httpSession.getAttribute(SessionConstants.CART_KEY);
        if(cart == null) {
            return 0.0;
        }
        return cart.stream().mapToDouble(p -> p.getCar().getPrice()).sum();
    }

    @Override
    public void addCarToCart(Long id) {
        Optional<Car> carBox = this.carDAO.findById(id);

        carBox.ifPresent(car -> {
            final Set<Position> cart = (Set<Position>) this.httpSession.getAttribute(SessionConstants.CART_KEY);
            cart.add(new Position(null, car));
        });
    }
}
