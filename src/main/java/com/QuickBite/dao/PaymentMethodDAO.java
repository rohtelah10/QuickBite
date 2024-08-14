package com.QuickBite.dao;

import com.QuickBite.model.PaymentMethod;
import com.QuickBite.model.User;

import java.util.List;

public interface PaymentMethodDAO {
    List<PaymentMethod> findByUser(User user);
    void save(PaymentMethod paymentMethod);
    void delete(PaymentMethod paymentMethod);
}
