package pl.edu.wszib.car.rental.session;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import pl.edu.wszib.car.rental.model.User;

@Component
@SessionScope
@Getter
@Setter
public class SessionObject {
    User user;
    String info;
    int cos;

    public boolean isLogged() {
        return this.user != null;
    }

    public boolean isAdmin() {
        return this.user != null && this.user.getRole() == User.Role.ADMIN;
    }
}
