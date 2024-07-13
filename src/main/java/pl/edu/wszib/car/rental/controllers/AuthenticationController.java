package pl.edu.wszib.car.rental.controllers;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.wszib.car.rental.exceptions.LoginAlreadyExistException;
import pl.edu.wszib.car.rental.model.User;
import pl.edu.wszib.car.rental.services.IAuthenticationService;
import pl.edu.wszib.car.rental.session.SessionConstants;

@Controller
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    @Autowired
    HttpSession httpSession;
    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String loginForm(Model model) {
        model.addAttribute("loginInfo", this.authenticationService.getLoginInfo());
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String login, @RequestParam String password) {
        this.authenticationService.login(login, password);
        if(this.httpSession.getAttribute(SessionConstants.USER_KEY) != null) {
            return "redirect:/";
        }
        return "redirect:/login";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout() {
        this.authenticationService.logout();
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String registerUser(@RequestParam String login, @RequestParam String name, @RequestParam String surname, @RequestParam String password) {
        try {
            this.authenticationService.registerUser(login, name, surname, password);
        } catch (LoginAlreadyExistException e) {
            return "redirect:/register?error";
        }
        return "redirect:/login";
    }
}
