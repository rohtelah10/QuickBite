package com.QuickBite.dao;

import com.QuickBite.model.Address;
import com.QuickBite.model.User;

import java.util.List;

public interface AddressDAO {
    void save(Address address);
    Address findById(Long addressId);
    void update(Address address);
    void delete(Long addressId);
    List<Address> findByUser(User user);  // New method to find addresses by user
}
