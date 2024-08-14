package com.QuickBite.daoImpl;

import com.QuickBite.model.Review;
import com.QuickBite.dao.ReviewDAO;
import com.QuickBite.model.Restaurant;
import com.QuickBite.model.User;

import util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReviewDAOImpl implements ReviewDAO {
    private static final Logger logger = Logger.getLogger(ReviewDAOImpl.class.getName());
    private SessionFactory sessionFactory;

    public ReviewDAOImpl() {
    	// Obtain a SessionFactory instance from the HibernateUtil
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<Review> findByRestaurant(Restaurant restaurant) {
        Session session = null;
        List<Review> reviews = null;
        try {
        	session = sessionFactory.openSession();
            reviews = session.createQuery("FROM Review r WHERE r.restaurant = :restaurant", Review.class)
                             .setParameter("restaurant", restaurant)
                             .getResultList();
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Error finding reviews by restaurant: " + e.getMessage(), e);
        } finally {
        	if(session!=null) {
            session.close();
        	}
        }
        return reviews;
    }

    @Override
    public List<Review> findByUser(User user) {
        Session session = null;
        List<Review> reviews = null;
        try {
        	session = sessionFactory.openSession();
            reviews = session.createQuery("FROM Review r WHERE r.user = :user", Review.class)
                             .setParameter("user", user)
                             .getResultList();
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Error finding reviews by user: " + e.getMessage(), e);
        } finally {
        	if(session!=null) {
                session.close();
            	}
            }
        return reviews;
    }

    @Override
    public void save(Review review) {
        Session session = null;
        Transaction transaction = null;
        try {
        	session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(review);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error saving review: " + e.getMessage(), e);
        } finally {
        	if(session!=null) {
                session.close();
            	}
            }
    }

    
}
