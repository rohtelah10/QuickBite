package com.QuickBite.service;

import com.QuickBite.dao.AddressDAO;
import com.QuickBite.daoImpl.AddressDAOImpl;
import com.QuickBite.model.Address;
import com.QuickBite.model.User;

import java.util.List;

public class AddressService {
    private AddressDAO addressDAO;

    public AddressService() {
        addressDAO = new AddressDAOImpl();
    }
    public AddressService(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    public void createAddress(Address address) {
        addressDAO.save(address);
    }

    public Address findById(Long addressId) {
        return addressDAO.findById(addressId);
    }

    public void updateAddress(Address address) {
        addressDAO.update(address);
    }

    public void deleteAddress(Long addressId) {
        addressDAO.delete(addressId);
    }

    public List<Address> findByUser(User user) {
        return addressDAO.findByUser(user);
    }

}
