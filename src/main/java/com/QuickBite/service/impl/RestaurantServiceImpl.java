package com.QuickBite.service.impl;

import com.QuickBite.dao.RestaurantDAO;
import com.QuickBite.model.Restaurant;
import com.QuickBite.service.RestaurantService;

import java.util.List;

public class RestaurantServiceImpl implements RestaurantService {
    private RestaurantDAO restaurantDAO;
    

    public RestaurantServiceImpl(RestaurantDAO restaurantDAO) {
        this.restaurantDAO = restaurantDAO;
    }

    @Override
    public void createRestaurant(Restaurant restaurant) {
        restaurantDAO.create(restaurant);
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantDAO.getById(id);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantDAO.getAll();
    }

    @Override
    public void updateRestaurant(Restaurant restaurant) {
        restaurantDAO.update(restaurant);
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantDAO.delete(id);
    }
    @Override
    public Restaurant findByUsername(String username) {
        return restaurantDAO.findByUsername(username);
    }
}
