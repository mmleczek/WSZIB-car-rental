package pl.edu.wszib.car.rental.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "tcar")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private String imgUrl;
    private double price;
    private int reserved;

    public Car(Long id) {
        this.id = id;
    }
}
