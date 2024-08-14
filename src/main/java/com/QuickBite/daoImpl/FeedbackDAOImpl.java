package com.QuickBite.daoImpl;

import com.QuickBite.dao.FeedbackDAO;
import com.QuickBite.model.Feedback;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class FeedbackDAOImpl implements FeedbackDAO {

    private SessionFactory sessionFactory;

    public FeedbackDAOImpl() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @Override
    public void saveFeedback(Feedback feedback) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(feedback);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
