package pl.edu.wszib.car.rental.dao.impl.spring.data;

import org.springframework.data.repository.CrudRepository;
import pl.edu.wszib.car.rental.model.User;

import java.util.Optional;

public interface UserDAO extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
