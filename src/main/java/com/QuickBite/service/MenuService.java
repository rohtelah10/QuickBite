package com.QuickBite.service;

import com.QuickBite.model.Menu;
import com.QuickBite.model.Restaurant;
import java.util.List;

public interface MenuService {
    void createMenu(Menu menu);
    Menu getMenuById(Long id);
    List<Menu> getMenuByRestaurant(Restaurant restaurant);
    void updateMenu(Menu menu); 
    void deleteMenu(Long id);
}
