package pl.edu.wszib.car.rental.services;

import pl.edu.wszib.car.rental.model.User;

public interface IAuthenticationService {
    void login(String login, String password);
    void logout();
    String getLoginInfo();
    void registerUser(String login, String name, String surname, String password);
}
