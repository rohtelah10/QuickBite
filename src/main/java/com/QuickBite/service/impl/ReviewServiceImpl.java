package com.QuickBite.service.impl;

import com.QuickBite.dao.ReviewDAO;
import com.QuickBite.model.Review;
import com.QuickBite.model.Restaurant;
import com.QuickBite.service.ReviewService;
import java.util.List;

public class ReviewServiceImpl implements ReviewService {
    private ReviewDAO reviewDAO;

    public ReviewServiceImpl(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    @Override
    public void createReview(Review review) {
        reviewDAO.save(review);
    }

    @Override
    public List<Review> getReviewsByRestaurant(Restaurant restaurant) {
        return reviewDAO.findByRestaurant(restaurant);
    }
}
