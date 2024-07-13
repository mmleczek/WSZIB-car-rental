package pl.edu.wszib.car.rental.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.wszib.car.rental.exceptions.CarValidationException;
import pl.edu.wszib.car.rental.model.Car;
import pl.edu.wszib.car.rental.services.ICarService;
import pl.edu.wszib.car.rental.validators.CarValidator;

import java.util.Optional;

@Controller
@RequestMapping(path = "/car")
public class CarController {

    private final ICarService carService;

    public CarController(ICarService carService) {
        this.carService = carService;
    }

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("carModel", new Car());
        return "carForm";
    }

    @PostMapping(path = "/add")
    public String add(@ModelAttribute Car car) {
        try {
            CarValidator.validateName(car.getName());
            CarValidator.validateBrand(car.getBrand());
            CarValidator.validateUrl(car.getImgUrl());
        } catch (CarValidationException e) {
            e.printStackTrace();
            return "redirect:/car/add";
        }
        this.carService.save(car);
        return "redirect:/";
    }

    @RequestMapping(path = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {
        Optional<Car> carBox = this.carService.getById(id);
        if(carBox.isEmpty()) {
            return "redirect:/";
        } else {
            model.addAttribute("carModel", carBox.get());
        }
        return "carForm";
    }

    @PostMapping(path = "/edit/{id}")
    public String edit(@ModelAttribute Car car, @PathVariable Long id) {
        try {
            CarValidator.validateName(car.getName());
            CarValidator.validateBrand(car.getBrand());
            CarValidator.validateUrl(car.getImgUrl());
        } catch (CarValidationException e) {
            e.printStackTrace();
            return "redirect:/car/edit/"+id;
        }
        this.carService.update(car, id);
        return "redirect:/";
    }
}
