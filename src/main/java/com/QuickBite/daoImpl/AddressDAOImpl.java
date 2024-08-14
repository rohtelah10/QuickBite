package com.QuickBite.daoImpl;

import com.QuickBite.dao.AddressDAO;

import com.QuickBite.model.Address;
import com.QuickBite.model.User;

import util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.query.Query; // Import Hibernate's Query class

import java.util.List;

public class AddressDAOImpl implements AddressDAO {
    private SessionFactory sessionFactory;

    public AddressDAOImpl() {
    	// Obtain a SessionFactory instance from the HibernateUtil
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public void save(Address address) {
        Session session = null;
        Transaction transaction = null;
        try {
        	session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(address);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error saving address: " + e.getMessage());
            throw new RuntimeException("Unable to save address", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Address findById(Long addressId) {
        Session session = null;
        Address address = null;
        try {
        	session = sessionFactory.openSession();
            address = session.get(Address.class, addressId);
        } catch (HibernateException e) {
            System.err.println("Error finding address by ID: " + e.getMessage());
            throw new RuntimeException("Unable to find address by ID", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return address;
    }

    @Override
    public void update(Address address) {
        Session session = null;
        Transaction transaction = null;
        try {
        	session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(address);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error updating address: " + e.getMessage());
            throw new RuntimeException("Unable to update address", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void delete(Long addressId) {
        Session session = null;
        Transaction transaction = null;
        try {
        	session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Address address = session.get(Address.class, addressId);
            if (address != null) {
                session.delete(address);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error deleting address: " + e.getMessage());
            throw new RuntimeException("Unable to delete address", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Address> findByUser(User user) {
        Session session = null;
        List<Address> addresses = null;
        try {
        	session = sessionFactory.openSession();
            String hql = "FROM Address a WHERE a.user = :user";
            Query<Address> query = session.createQuery(hql, Address.class); // Use Hibernate's Query class
            query.setParameter("user", user);
            addresses = query.getResultList();
        } catch (HibernateException e) {
            System.err.println("Error finding addresses by user: " + e.getMessage());
            throw new RuntimeException("Unable to find addresses by user", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return addresses;
    }

   
}
