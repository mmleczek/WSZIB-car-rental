package pl.edu.wszib.car.rental.services;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import pl.edu.wszib.car.rental.dao.impl.spring.data.CarDAO;
import pl.edu.wszib.car.rental.dao.impl.spring.data.UserDAO;
import pl.edu.wszib.car.rental.model.Car;
import pl.edu.wszib.car.rental.model.User;

@Service
@RequiredArgsConstructor
public class DataInitialization implements CommandLineRunner {
    private final CarDAO carDAO;
    private final UserDAO userDAO;

    @Override
    public void run(String... args) throws Exception {
        this.userDAO.save(new User(null, "Janusz", "Kowalski",
                "janusz", DigestUtils.md5DigestAsHex("janusz123".getBytes()), User.Role.USER));
        this.userDAO.save(new User(null, "Wiesiek", "Admin",
                "wiesiek", DigestUtils.md5DigestAsHex("wiesiek123".getBytes()), User.Role.ADMIN));
        this.userDAO.save(new User(null, "admin", "admin",
                "admin", DigestUtils.md5DigestAsHex("admin".getBytes()), User.Role.ADMIN));

        this.carDAO.save(new Car(null, "M3 COMPETITION", "BMW", "https://i.imgur.com/qjkXCL9.png", 1000.0, 0));
        this.carDAO.save(new Car(null, "RS3", "AUDI", "https://i.imgur.com/gxDGlIs.jpg", 1200.0, 0));
        this.carDAO.save(new Car(null, "RX7", "MAZDA", "https://i.imgur.com/800m44s.jpg", 1500.0, 0));
    }
}
