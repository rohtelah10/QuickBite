package com.QuickBite.daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.QuickBite.dao.RestaurantDAO;
import com.QuickBite.model.Restaurant;

import util.HibernateUtil;

public class RestaurantDAOImpl implements RestaurantDAO {
    private SessionFactory sessionFactory;

    public RestaurantDAOImpl() {
        // Obtain a SessionFactory instance from the HibernateUtil
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public void create(Restaurant restaurant) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(restaurant);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Restaurant getById(Long id) {
        Session session = null;
        Restaurant restaurant = null;
        try {
            session = sessionFactory.openSession();
            restaurant = session.get(Restaurant.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return restaurant;
    }
    
    @Override
    public Restaurant findByUsername(String name) {
        Session session = null;
        Restaurant restaurant = null;
        try {
            session = sessionFactory.openSession();
            String hql = "FROM Restaurant WHERE name = :name";
            Query<Restaurant> query = session.createQuery(hql, Restaurant.class);
            query.setParameter("name", name);
            restaurant = query.uniqueResult(); // Using uniqueResult() to get a single result
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return restaurant;
    }
    @Override
    public List<Restaurant> getAll() {
        Session session = null;
        List<Restaurant> restaurants = null;
        try {
            session = sessionFactory.openSession();
            restaurants = session.createQuery("from Restaurant", Restaurant.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return restaurants;
    }

    @Override
    public void update(Restaurant restaurant) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(restaurant);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void delete(Long id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Restaurant restaurant = session.get(Restaurant.class, id);
            if (restaurant != null) {
                session.delete(restaurant);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    
}
