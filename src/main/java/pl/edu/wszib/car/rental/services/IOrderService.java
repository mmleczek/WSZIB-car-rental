package pl.edu.wszib.car.rental.services;

import pl.edu.wszib.car.rental.model.Order;

import java.util.List;

public interface IOrderService {
    void confirmOrder();
    List<Order> getOrdersForCurrentUser();
    List<Order> getAllOrders();
}
