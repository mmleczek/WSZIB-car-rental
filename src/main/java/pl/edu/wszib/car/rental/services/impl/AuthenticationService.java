package pl.edu.wszib.car.rental.services.impl;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import pl.edu.wszib.car.rental.dao.impl.spring.data.UserDAO;
import pl.edu.wszib.car.rental.exceptions.LoginAlreadyExistException;
import pl.edu.wszib.car.rental.model.User;
import pl.edu.wszib.car.rental.services.IAuthenticationService;
import pl.edu.wszib.car.rental.session.SessionConstants;
import pl.edu.wszib.car.rental.session.SessionObject;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final UserDAO userDAO;
    private final HttpSession httpSession;

    @Resource
    SessionObject sessionObject;

    @Override
    public void login(String login, String password) {
        Optional<User> user = this.userDAO.findByLogin(login);

        if(user.isPresent() &&
                DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.get().getPassword())) {
            httpSession.setAttribute(SessionConstants.USER_KEY, user.get());
            httpSession.setAttribute(SessionConstants.CART_KEY, new HashSet<>());
            sessionObject.setUser(user.get());
            return;
        }
        this.httpSession.setAttribute("loginInfo", "złe dane");
    }

    @Override
    public void logout() {
        this.httpSession.removeAttribute(SessionConstants.USER_KEY);
        this.httpSession.removeAttribute(SessionConstants.CART_KEY);
        sessionObject.setUser(null);
    }

    @Override
    public String getLoginInfo() {
        String temp = (String) this.httpSession.getAttribute("loginInfo");
        this.httpSession.removeAttribute("loginInfo");
        return temp;
    }

    @Override
    public void registerUser(String login, String name, String surname, String password) {
        Optional<User> user = this.userDAO.findByLogin(login);
        if(user.isPresent()) {
            throw new LoginAlreadyExistException();
        }
        System.out.println("password " + password);
        this.userDAO.save(new User(null, name, surname,
                login, DigestUtils.md5DigestAsHex(password.getBytes()), User.Role.USER));
    }
}
