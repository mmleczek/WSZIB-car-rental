package pl.edu.wszib.car.rental.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.wszib.car.rental.services.ICartService;

@Controller
@RequestMapping(path = "/cart")
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;

    @Autowired
    HttpSession httpSession;

    @GetMapping(path = "/add/{id}")
    public String addToCart(@PathVariable final Long id) {
        this.cartService.addCarToCart(id);
        return "redirect:/";
    }

    @GetMapping
    public String cart(Model model) {
        model.addAttribute("cartSum", this.cartService.calculateCartSum());
        return "cart";
    }
}
