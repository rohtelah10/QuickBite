package com.QuickBite.service;

import com.QuickBite.model.Restaurant;
import java.util.List;

public interface RestaurantService {
    void createRestaurant(Restaurant restaurant);
    Restaurant getRestaurantById(Long id);
    List<Restaurant> getAllRestaurants();
    void updateRestaurant(Restaurant restaurant);
    void deleteRestaurant(Long id);
    Restaurant findByUsername(String username);
}
