package pl.edu.wszib.car.rental.dao.impl.spring.data;

import org.springframework.data.repository.CrudRepository;
import pl.edu.wszib.car.rental.model.Order;

public interface OrderDAO extends CrudRepository<Order, Long> {
}
