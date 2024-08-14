package com.QuickBite.dao;

import com.QuickBite.model.Menu;
import com.QuickBite.model.Restaurant;

import java.util.List;

public interface MenuDAO {
    void save(Menu menu);
    Menu findById(Long id);
    List<Menu> findByRestaurant(Restaurant restaurant);
    List<Menu> getAll();
    void update(Menu menu);
    void delete(Menu menu);
}
