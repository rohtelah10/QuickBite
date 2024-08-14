package com.QuickBite.daoImpl;

import com.QuickBite.dao.PaymentMethodDAO;
import com.QuickBite.model.PaymentMethod;
import com.QuickBite.model.User;

import util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

import java.util.List;

public class PaymentMethodDAOImpl implements PaymentMethodDAO {
    private SessionFactory sessionFactory;

    public PaymentMethodDAOImpl() {
    	// Obtain a SessionFactory instance from the HibernateUtil
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<PaymentMethod> findByUser(User user) {
        Session session = null;
        List<PaymentMethod> paymentMethods = null;
        try {
        	session = sessionFactory.openSession();
            paymentMethods = session.createQuery("FROM PaymentMethod p WHERE p.user = :user", PaymentMethod.class)
                                    .setParameter("user", user)
                                    .getResultList();
        } catch (HibernateException e) {
            System.err.println("Error finding payment methods by user: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return paymentMethods;
    }

    @Override
    public void save(PaymentMethod paymentMethod) {
        Session session =null;
        Transaction transaction = null;
        try {
        	session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(paymentMethod);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error saving payment method: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void delete(PaymentMethod paymentMethod) {
        Session session = null;
        Transaction transaction = null;
        try {
        	session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            PaymentMethod managedPaymentMethod = session.contains(paymentMethod)? paymentMethod :(PaymentMethod) session.merge(paymentMethod);
            session.delete(managedPaymentMethod);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error deleting payment method: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    
}
