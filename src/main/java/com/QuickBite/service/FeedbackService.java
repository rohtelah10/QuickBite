package com.QuickBite.service;

import com.QuickBite.dao.FeedbackDAO;
import com.QuickBite.daoImpl.FeedbackDAOImpl;
import com.QuickBite.model.Feedback;

public class FeedbackService {

    private FeedbackDAO feedbackDAO;

    public FeedbackService() {
        feedbackDAO = new FeedbackDAOImpl();
    }

    public void submitFeedback(String userName, String comments) {
        Feedback feedback = new Feedback(userName, comments, new java.util.Date());
        feedbackDAO.saveFeedback(feedback);
    }
}
