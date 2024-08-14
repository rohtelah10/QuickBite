package com.QuickBite.model;

import javax.persistence.*;

@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "category")
    private String category;

	public Menu() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Menu( String itemName, double price ,Restaurant restaurant) {
		super();
		this.restaurant = restaurant;
		this.itemName = itemName;
		this.price = price;
		
	}
	public Menu(Long menuId, Restaurant restaurant, String itemName, String description, double price,
			String category) {
		super();
		this.menuId = menuId;
		this.restaurant = restaurant;
		this.itemName = itemName;
		this.description = description;
		this.price = price;
		this.category = category;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	} 
    
}
