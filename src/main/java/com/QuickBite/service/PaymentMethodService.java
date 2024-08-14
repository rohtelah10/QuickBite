package com.QuickBite.service;

import java.util.List;
import com.QuickBite.dao.PaymentMethodDAO;
import com.QuickBite.model.PaymentMethod;
import com.QuickBite.model.User;

public class PaymentMethodService {
    private final PaymentMethodDAO paymentMethodDAO;

    public PaymentMethodService(PaymentMethodDAO paymentMethodDAO) {
        this.paymentMethodDAO = paymentMethodDAO;
    }

    public List<PaymentMethod> getPaymentMethodsByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return paymentMethodDAO.findByUser(user);
    }

    public void addPaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            throw new IllegalArgumentException("PaymentMethod cannot be null");
        }
        paymentMethodDAO.save(paymentMethod);
    }

    public void removePaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod == null || paymentMethod.getPaymentMethodId() == null) {
            throw new IllegalArgumentException("PaymentMethod or PaymentMethod ID cannot be null");
        }
        paymentMethodDAO.delete(paymentMethod);
    }
}
