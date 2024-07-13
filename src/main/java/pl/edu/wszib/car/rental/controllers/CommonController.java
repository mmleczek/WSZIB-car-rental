package pl.edu.wszib.car.rental.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.wszib.car.rental.session.SessionObject;
import pl.edu.wszib.car.rental.services.ICarService;

@Controller
public class CommonController {

    private final ICarService carService;
    private final SessionObject sessionObject;

    public CommonController(ICarService carService, SessionObject sessionObject) {
        this.carService = carService;
        this.sessionObject = sessionObject;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index(Model model, @RequestParam(required = false) String pattern) {
        boolean isAdmin = sessionObject.isAdmin();
        if(pattern == null || pattern.isEmpty()) {
            model.addAttribute("cars", this.carService.getAll(isAdmin));
            model.addAttribute("pattern", "");
        } else {
            model.addAttribute("cars", this.carService.getByPattern(pattern, isAdmin));
            model.addAttribute("pattern", pattern);
        }
        return "index";
    }

    @RequestMapping(path = "/contact", method = RequestMethod.GET)
    public String contact() {
        return "contact";
    }
}
