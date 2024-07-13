package pl.edu.wszib.car.rental.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.edu.wszib.car.rental.exceptions.EmptyCartException;
import pl.edu.wszib.car.rental.exceptions.IncorrectCartPositionsException;
import pl.edu.wszib.car.rental.exceptions.UserNotLoggedException;
import pl.edu.wszib.car.rental.services.IOrderService;
import pl.edu.wszib.car.rental.session.SessionObject;

@Controller
public class OrderController {
    private final IOrderService orderService;
    private final SessionObject sessionObject;

    public OrderController(IOrderService orderService, SessionObject sessionObject) {
        this.orderService = orderService;
        this.sessionObject = sessionObject;
    }

    @GetMapping("/order/add")
    public String order() {
        try {
            this.orderService.confirmOrder();
        } catch (EmptyCartException | IncorrectCartPositionsException e) {
            return "redirect:/cart";
        }
        return "redirect:/";
    }

    @GetMapping("/order")
    public String orders(Model model) {
        try {
            if (sessionObject.isAdmin()) {
                model.addAttribute("orders", this.orderService.getAllOrders());
            } else {
                model.addAttribute("orders", this.orderService.getOrdersForCurrentUser());
            }
        } catch (UserNotLoggedException e) {
            return "redirect:/";
        }
        return "orders";
    }
}
