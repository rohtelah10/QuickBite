package com.QuickBite.service;

import com.QuickBite.model.Review;
import com.QuickBite.model.Restaurant;
import java.util.List;

public interface ReviewService {
    void createReview(Review review);
    List<Review> getReviewsByRestaurant(Restaurant restaurant);
}
