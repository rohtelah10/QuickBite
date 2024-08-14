package com.QuickBite.service;

import java.util.List;
import com.QuickBite.dao.OrderDAO;
import com.QuickBite.model.Order;
import com.QuickBite.model.Restaurant;
import com.QuickBite.model.User;

public class OrderService {
    private final OrderDAO orderDAO;

    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void createOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        orderDAO.save(order);
    }

    public List<Order> getOrdersByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return orderDAO.findByUser(user);
    }

    public Order getOrderById(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        return orderDAO.findById(orderId);
    }

    public void updateOrder(Order order) {
        if (order == null || order.getOrderId() == null) {
            throw new IllegalArgumentException("Order or Order ID cannot be null");
        }
        orderDAO.update(order);
    }

    public void deleteOrder(Order order) {
        if (order == null || order.getOrderId() == null) {
            throw new IllegalArgumentException("Order or Order ID cannot be null");
        }
        orderDAO.delete(order);
    }

    public List<Order> getAllOrders(Restaurant restaurant) {
        return orderDAO.getAllOrders(restaurant);
    }
    
    public List<Order> getAllPendingOrders(Restaurant restaurant) {
        return orderDAO.findAllPendingOrders(restaurant);
    }

    public void markOrderAsDelivered(Long orderId) {
        Order order = orderDAO.findById(orderId);
        if (order != null) {
            order.setStatus("Delivered"); 
            updateOrder(order);
        } else {
            throw new IllegalArgumentException("Order not found");
        }
    }
    
    
}
