package com.QuickBite.daoImpl;

import com.QuickBite.dao.MenuDAO;

import com.QuickBite.model.Menu;
import com.QuickBite.model.Restaurant;

import util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.query.Query; // Import the Hibernate Query class

import java.util.List;

public class MenuDAOImpl implements MenuDAO {
    private SessionFactory sessionFactory;

    public MenuDAOImpl() {
    	// Obtain a SessionFactory instance from the HibernateUtil
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public void save(Menu menu) {
        Session session = null;
        Transaction transaction = null;
        try {
        	session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(menu);
            transaction.commit();
        } catch (HibernateException e) {
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
    public Menu findById(Long id) {
        Session session = null;
        Menu menu = null;
        try {
        	session = sessionFactory.openSession();
            menu = session.get(Menu.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return menu;
    }

    @Override
    public List<Menu> findByRestaurant(Restaurant restaurant) {
        Session session = null;
        List<Menu> menus = null;
        try {
        	session = sessionFactory.openSession();
            String hql = "FROM Menu m WHERE m.restaurant = :restaurant";
            Query<Menu> query = session.createQuery(hql, Menu.class); // Use Hibernate's Query class
            query.setParameter("restaurant", restaurant);
            menus = query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return menus;
    }

    @Override
    public List<Menu> getAll() {
        Session session = null;
        List<Menu> menus = null;
        try {
        	session = sessionFactory.openSession();
            String hql = "FROM Menu";
            Query<Menu> query = session.createQuery(hql, Menu.class); // Use Hibernate's Query class
            menus = query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return menus;
    }

    @Override
    public void update(Menu menu) {
        Session session = null;
        Transaction transaction = null;
        try {
        	session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(menu);
            transaction.commit();
        } catch (HibernateException e) {
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
    public void delete(Menu menu) {
        Session session = null;
        Transaction transaction = null;
        try {
        	session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            if (session.contains(menu)) {
                session.delete(menu);
            } else {
                session.delete(session.merge(menu));
            }
            transaction.commit();
        } catch (HibernateException e) {
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
