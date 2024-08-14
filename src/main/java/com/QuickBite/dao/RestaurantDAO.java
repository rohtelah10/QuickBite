package com.QuickBite.dao;

import com.QuickBite.model.Restaurant;

import java.util.List;

public interface RestaurantDAO {
    void create(Restaurant restaurant);
    Restaurant getById(Long id);
    List<Restaurant> getAll();
    void update(Restaurant restaurant);
    void delete(Long id);
    Restaurant findByUsername(String username);
}
