package com.QuickBite.service;

import com.QuickBite.dao.UserDAO;
import com.QuickBite.model.User;

public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User findById(Long userId) {
        return userDAO.findById(userId);
    }

    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public void createUser(User user) {
        userDAO.save(user);
    }

    public void updateUser(User user) {
        userDAO.update(user);
    }

    public void deleteUser(User user) {
        userDAO.delete(user);
    }

    
}
