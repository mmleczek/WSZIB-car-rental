package pl.edu.wszib.car.rental.validators;

import pl.edu.wszib.car.rental.exceptions.CarValidationException;

public class CarValidator {

    public static void validateName(String title) {
        String regex = "^[A-Z].*$";
        if(!title.matches(regex)) {
            throw new CarValidationException();
        }
    }

    public static void validateBrand(String author) {
        String regex = "^[A-Z].*$";
        if(!author.matches(regex)) {
            throw new CarValidationException();
        }
    }

    public static void validateUrl(String link) {
        String regex = ".*\\.(png|jpg|webp)$";
        if(!link.matches(regex)) {
            throw new CarValidationException();
        }
    }
}
