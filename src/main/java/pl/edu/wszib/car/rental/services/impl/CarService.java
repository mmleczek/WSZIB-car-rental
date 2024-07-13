package pl.edu.wszib.car.rental.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wszib.car.rental.dao.impl.spring.data.CarDAO;
import pl.edu.wszib.car.rental.model.Car;
import pl.edu.wszib.car.rental.services.ICarService;
import jakarta.annotation.Resource;
import pl.edu.wszib.car.rental.session.SessionObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService implements ICarService {
    private final CarDAO carDAO;

    @Resource
    private SessionObject sessionObject;

    @Override
    public void save(Car car) {
        this.carDAO.save(car);
    }

    @Override
    public Optional<Car> getById(Long id) {
        return this.carDAO.findById(id);
    }

    @Override
    public void update(Car car, Long id) {
        car.setId(id);
        this.carDAO.save(car);
    }

    @Override
    public List<Car> getAll(boolean isAdmin) {
        List<Car> list = new ArrayList<>();
        if (isAdmin) {
            this.carDAO.findAll().forEach(list::add);
        } else {
            list = this.carDAO.findAllAvailable();
        }
        return list;
    }

    @Override
    public List<Car> getByPattern(String pattern, boolean isAdmin) {
        if (isAdmin) {
            return this.carDAO.findByNameLikeOrBrandLike("%" + pattern + "%");
        } else {
            return this.carDAO.findAvailableByNameLikeOrBrandLike("%" + pattern + "%");
        }
    }
}
