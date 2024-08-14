package com.QuickBite.service.impl;

import com.QuickBite.dao.MenuDAO;
import com.QuickBite.model.Menu;
import com.QuickBite.model.Restaurant;
import com.QuickBite.service.MenuService;
import java.util.List;

public class MenuServiceImpl implements MenuService {
    private MenuDAO menuDAO;

    public MenuServiceImpl(MenuDAO menuDAO) {
        this.menuDAO = menuDAO;
    }

    @Override
    public void createMenu(Menu menu) {
        menuDAO.save(menu);
    }

    @Override
    public Menu getMenuById(Long id) {
        return menuDAO.findById(id);
    }

    @Override
    public List<Menu> getMenuByRestaurant(Restaurant restaurant) {
        return menuDAO.findByRestaurant(restaurant);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuDAO.update(menu);
    }
    @Override
    public void deleteMenu(Long id) {
        Menu menu = menuDAO.findById(id);
        if (menu != null) {
            menuDAO.delete(menu);
        } else {
            throw new IllegalArgumentException("Menu with ID " + id + " not found.");
        }
    }
}
