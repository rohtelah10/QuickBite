package com.QuickBite.dao;

import com.QuickBite.model.User;

public interface UserDAO {
    User findById(Long userId);
    User findByUsername(String username);
    void save(User user);
    void update(User user);
    void delete(User user);
}
