package com.QuickBite.dao;

import com.QuickBite.model.Order;
import com.QuickBite.model.Restaurant;
import com.QuickBite.model.User;
import java.util.List;

public interface OrderDAO {
    void save(Order order);
    Order findById(Long id);
    List<Order> findByUser(User user);
    List<Order> getAllOrders(Restaurant restaurant);
    List<Order> findAllPendingOrders(Restaurant restaurant);
    void update(Order order);
    void delete(Order order);
}
