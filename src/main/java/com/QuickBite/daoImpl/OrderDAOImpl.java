package com.QuickBite.daoImpl;

import com.QuickBite.dao.OrderDAO;
import com.QuickBite.model.Order;
import com.QuickBite.model.Restaurant;
import com.QuickBite.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private SessionFactory sessionFactory;

    public OrderDAOImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<Order> findByUser(User user) {
        Session session = null;
        List<Order> orders = null;
        try {
            session = sessionFactory.openSession();
            String hql = "FROM Order o WHERE o.user = :user";
            Query<Order> query = session.createQuery(hql, Order.class);
            query.setParameter("user", user);
            orders = query.getResultList();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return orders;
    }

    @Override
    public Order findById(Long orderId) {
        Session session = null;
        Order order = null;
        try {
            session = sessionFactory.openSession();
            order = session.get(Order.class, orderId);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return order;
    }

    @Override
    public void save(Order order) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(order);
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
    public void update(Order order) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(order);
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
    public void delete(Order order) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.delete(session.contains(order) ? order : session.merge(order));
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
    public List<Order> findAllPendingOrders(Restaurant restaurant) {
        Session session = null;
        List<Order> orders = null;
        try {
            session = sessionFactory.openSession();
            String hql = "FROM Order o WHERE o.restaurant = :restaurant AND o.status = 'Pending'";
            Query<Order> query = session.createQuery(hql, Order.class);
            query.setParameter("restaurant", restaurant);
            orders = query.getResultList();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return orders;
    }

    @Override
    public List<Order> getAllOrders(Restaurant restaurant) {
        Session session = null;
        List<Order> orders = null;
        try {
            session = sessionFactory.openSession();
            String hql = "FROM Order o WHERE o.restaurant = :restaurant";
            Query<Order> query = session.createQuery(hql, Order.class);
            query.setParameter("restaurant", restaurant);
            orders = query.getResultList();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return orders;
    }

   
}
