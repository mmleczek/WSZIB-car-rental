package pl.edu.wszib.car.rental.services;

import pl.edu.wszib.car.rental.model.Car;

import java.util.List;
import java.util.Optional;

public interface ICarService {
    void save(Car car);
    Optional<Car> getById(Long id);
    void update(Car car, Long id);
    List<Car> getAll(boolean isAdmin);

    List<Car> getByPattern(String pattern, boolean isAdmin);
}
