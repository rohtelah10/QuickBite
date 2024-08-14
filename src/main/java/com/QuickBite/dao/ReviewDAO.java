package com.QuickBite.dao;

import com.QuickBite.model.Review;
import com.QuickBite.model.Restaurant;
import com.QuickBite.model.User;

import java.util.List;

public interface ReviewDAO {
    List<Review> findByRestaurant(Restaurant restaurant);
    List<Review> findByUser(User user);
    void save(Review review);
}
