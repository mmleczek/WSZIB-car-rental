package pl.edu.wszib.car.rental.dao.impl.spring.data;

import org.springframework.data.repository.CrudRepository;
import pl.edu.wszib.car.rental.model.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarDAO extends CrudRepository<Car, Long> {
    @Query("SELECT c FROM tcar c WHERE LOWER(c.name) LIKE LOWER(:pattern1) OR LOWER(c.brand) LIKE LOWER(:pattern2)")
    List<Car> findByNameLikeOrBrandLike(@Param("pattern1") String pattern1, @Param("pattern2") String pattern2);

    @Query("SELECT c FROM tcar c WHERE c.reserved = 0 AND (LOWER(c.name) LIKE LOWER(:pattern1) OR LOWER(c.brand) LIKE LOWER(:pattern2))")
    List<Car> findAvailableByNameLikeOrBrandLike(@Param("pattern1") String pattern1, @Param("pattern2") String pattern2);

    @Query("SELECT c FROM tcar c WHERE c.reserved = 0")
    List<Car> findAllAvailable();

    default List<Car> findByNameLikeOrBrandLike(String pattern) {
        return findByNameLikeOrBrandLike(pattern, pattern);
    }

    default List<Car> findAvailableByNameLikeOrBrandLike(String pattern) {
        return findAvailableByNameLikeOrBrandLike(pattern, pattern);
    }
}
