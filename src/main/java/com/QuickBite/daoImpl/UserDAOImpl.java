package com.QuickBite.daoImpl;

import com.QuickBite.dao.UserDAO;
import com.QuickBite.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import util.HibernateUtil;

public class UserDAOImpl implements UserDAO {

    private SessionFactory sessionFactory ;

    public UserDAOImpl() {
        // Obtain a SessionFactory instance from the HibernateUtil
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public User findById(Long userId) {
        Session session = null;
        User user = null;
        try {
        	session = sessionFactory.openSession();
            user = session.get(User.class, userId);
        } catch (HibernateException e) {
            System.err.println("Error finding user by ID: " + e.getMessage());
            throw new RuntimeException("Unable to find user by ID", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public User findByUsername(String username) {
        Session session = null;
        User user = null;
        try {
        	session = sessionFactory.openSession();
            user = (User) session.createQuery("FROM User u WHERE u.username = :username")
                                 .setParameter("username", username)
                                 .uniqueResult();
        } catch (HibernateException e) {
            System.err.println("Error finding user by username: " + e.getMessage());
            throw new RuntimeException("Unable to find user by username", e);
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public void save(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
        	session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error saving user: " );
            throw new RuntimeException("Unable to save user", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void update(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
        	session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error updating user: " + e.getMessage());
            throw new RuntimeException("Unable to update user", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void delete(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
        	session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.delete(session.contains(user) ? user : session.merge(user));
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error deleting user: " + e.getMessage());
            throw new RuntimeException("Unable to delete user", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
