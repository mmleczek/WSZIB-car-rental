package pl.edu.wszib.car.rental.services;

public interface ICartService {
    double calculateCartSum();
    void addCarToCart(Long id);
}
