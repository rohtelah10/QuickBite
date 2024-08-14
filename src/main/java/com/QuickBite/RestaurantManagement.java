package com.QuickBite;

import java.util.List;
import java.util.regex.Pattern;
import java.util.Scanner;

import com.QuickBite.Inputs.Inputs;
import com.QuickBite.Print.Print;
import com.QuickBite.dao.MenuDAO;
import com.QuickBite.dao.OrderDAO;
import com.QuickBite.dao.RestaurantDAO;
import com.QuickBite.dao.ReviewDAO;
import com.QuickBite.daoImpl.MenuDAOImpl;
import com.QuickBite.daoImpl.OrderDAOImpl;
import com.QuickBite.daoImpl.RestaurantDAOImpl;
import com.QuickBite.daoImpl.ReviewDAOImpl;
import com.QuickBite.menu.AllRestaurantMenu;
import com.QuickBite.model.Menu;
import com.QuickBite.model.Order;
import com.QuickBite.model.Restaurant;
import com.QuickBite.model.Review;
import com.QuickBite.service.MenuService;
import com.QuickBite.service.OrderService;
import com.QuickBite.service.RestaurantService;
import com.QuickBite.service.ReviewService;
import com.QuickBite.service.impl.MenuServiceImpl;
import com.QuickBite.service.impl.RestaurantServiceImpl;
import com.QuickBite.service.impl.ReviewServiceImpl;

public class RestaurantManagement {
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;
    private final MenuService menuService;
    private final OrderService orderService;
    private final AllRestaurantMenu restaurantMenu;
    private final Inputs input;
    private final Print print;
    private Restaurant restaurant;

    public RestaurantManagement() {
        super();
        RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
        ReviewDAO reviewDAO = new ReviewDAOImpl();
        MenuDAO menuDAO = new MenuDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();

        // Initialize services with DAOs
        restaurantService = new RestaurantServiceImpl(restaurantDAO);
        reviewService = new ReviewServiceImpl(reviewDAO);
        menuService = new MenuServiceImpl(menuDAO);
        orderService = new OrderService(orderDAO);

        // Menus
        restaurantMenu = new AllRestaurantMenu();

        // Input functions
        input = new Inputs();

        // Print functions
        print = new Print();
    }

    public void handleRestaurantManagement(Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            restaurantMenu.printRestaurantManagementMenu();
            int choice = input.getIntInput(scanner);

            switch (choice) {
                case 1:
                    openNewRestaurant(scanner);
                    break;
                case 2:
                	loginAsRestaurant(scanner);
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private void openNewRestaurant(Scanner scanner) {
        System.out.println("----------------------------------------------");
        System.out.println("---------------New Restaurant-----------------");
        System.out.println();
        
        scanner.nextLine();
        
        System.out.print("Enter restaurant name (required): ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Error: Restaurant name cannot be empty.");
            return;
        }

        System.out.print("Enter restaurant password (required): ");
        String password = scanner.nextLine().trim();
        if (password.isEmpty()) {
            System.out.println("Error: Password cannot be empty.");
            return;
        }
        if (password.length() < 8) {
            System.out.println("Error: Password must be at least 8 characters long.");
            return;
        }
        if (!Pattern.compile("[0-9]").matcher(password).find() || 
            !Pattern.compile("[a-z]").matcher(password).find() || 
            !Pattern.compile("[A-Z]").matcher(password).find()) {
            System.out.println("Error: Password must contain at least one digit, one lowercase letter, and one uppercase letter.");
            return;
        }

        System.out.print("Enter restaurant address (required): ");
        String address = scanner.nextLine().trim();
        if (address.isEmpty()) {
            System.out.println("Error: Address cannot be empty.");
            return;
        }

        System.out.print("Enter restaurant phone (required): ");
        String phone = scanner.nextLine().trim();
        if (phone.isEmpty()) {
            System.out.println("Error: Phone number cannot be empty.");
            return;
        }
        if (!Pattern.compile("\\d{10}").matcher(phone).matches()) {
            System.out.println("Error: Phone number must be exactly 10 digits.");
            return;
        }

        System.out.print("Enter restaurant email (required): ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty()) {
            System.out.println("Error: Email cannot be empty.");
            return;
        }
        if (!Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$").matcher(email).matches()) {
            System.out.println("Error: Invalid email format.");
            return;
        }

        System.out.print("Enter restaurant opening hours (optional): ");
        String openingHours = scanner.nextLine().trim();

        System.out.print("Enter restaurant cuisine type (optional): ");
        String cuisineType = scanner.nextLine().trim();

        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setPassword(password);
        restaurant.setAddress(address);
        restaurant.setPhone(phone);
        restaurant.setEmail(email);
        restaurant.setOpeningHours(openingHours);
        restaurant.setCuisineType(cuisineType);
        
        restaurantService.createRestaurant(restaurant);
        System.out.println("Restaurant opened successfully.");
    }

    private void loginAsRestaurant(Scanner scanner) {
        boolean exitManagement = false;
        login(scanner);
        while (!exitManagement) {
                restaurantMenu.printManageRestaurantMenu();
                int choice = input.getIntInput(scanner);

                switch (choice) {
                    case 1:
                        addMenuItem(scanner, restaurant);
                        break;
                    case 2:
                        removeMenuItem(scanner, restaurant);
                        break;
                    case 3:
                        viewAllReviews(restaurant);
                        break;
                    case 4:
                        manageOrders(scanner);
                        break;
                    case 5:
                        exitManagement = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            
        }
    }
    public void login(Scanner scanner) {
    	scanner.nextLine();
    	System.out.println("----------------------------------------------");
    	System.out.println("--------------------Login---------------------");
    	System.out.println();
        System.out.print("Enter restaurant username:");
        String username = scanner.nextLine().trim();

        System.out.print("Enter restaurant password:");
        String password = scanner.nextLine().trim(); 

        restaurant = restaurantService.findByUsername(username);
        System.out.println();
        if (restaurant != null && restaurant.getPassword().equals(password)) {
            System.out.println("Login successful. Welcome, " + username + "!");
            return;
        } else {
            System.out.println("Invalid username or password.");
            handleRestaurantManagement(scanner);
        }
    }

    private void addMenuItem(Scanner scanner, Restaurant restaurant) {
    	scanner.nextLine();
    	System.out.println("----------------------------------------------");
    	System.out.println("---------------Add Menu Item------------------");
    	System.out.println();
        System.out.print("Enter item name:");
        String itemName = scanner.nextLine();

        System.out.print("Enter description:");
        String description = scanner.nextLine();

        System.out.print("Enter price:");
        double price = input.getDoubleInput(scanner);
        scanner.nextLine();
        System.out.print("Enter category:");
        String category = scanner.nextLine();

        Menu menu = new Menu();
        menu.setItemName(itemName);
        menu.setDescription(description);
        menu.setPrice(price);
        menu.setCategory(category);
        menu.setRestaurant(restaurant);

        menuService.createMenu(menu);
        System.out.println("Menu item added successfully.");
    }

    private void removeMenuItem(Scanner scanner, Restaurant restaurant) {
        List<Menu> menuItems = menuService.getMenuByRestaurant(restaurant);

        if (menuItems.isEmpty()) {
            System.out.println("No menu items available to remove.");
            return;
        }
        print.printMenuItems(menuItems);
        System.out.println("----------------------------------------------");
        System.out.println("--------------Remove Menu Item----------------");
        System.out.println();
        System.out.println("Enter menu item ID to remove:");
        Long menuId = input.getLongInput(scanner);
        Menu menuItemToRemove = menuItems.stream()
                .filter(m -> m.getMenuId().equals(menuId))
                .findFirst()
                .orElse(null);

        if (menuItemToRemove != null) {
            menuService.deleteMenu(menuId);
            System.out.println("Menu item removed successfully.");
        } else {
            System.out.println("Menu item with ID " + menuId + " not found.");
        }
    }


    private void viewAllReviews(Restaurant restaurant) {
    	System.out.println("----------------------------------------------");
    	System.out.println("--------------View All Reviews----------------");
    	System.out.println();
        List<Review> reviews = reviewService.getReviewsByRestaurant(restaurant);
        print.printReviews(reviews);
    }

    private void manageOrders(Scanner scanner) {
        boolean exitOrderManagement = false;

        while (!exitOrderManagement) {
        	System.out.println("----------------------------------------------");
        	System.out.println("---------------Manage Orders------------------");
        	System.out.println();
            System.out.println("1. View all pending orders");
            System.out.println("2. View all orders");
            System.out.println("3. Back to restaurant management menu");

            int choice = input.getIntInput(scanner);

            switch (choice) {
                case 1:
                    viewAllPendingOrders(scanner);
                    break;
                case 2:
                    viewAllOrders(scanner);
                    break;
                case 3:
                    exitOrderManagement = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private void viewAllPendingOrders(Scanner scanner) {
        List<Order> pendingOrders = orderService.getAllPendingOrders(restaurant);
        System.out.println("----------------------------------------------");
    	System.out.println("-------------All Pending Orders---------------");
        print.printOrders(pendingOrders,scanner , orderService);
    }

    private void viewAllOrders(Scanner scanner) {
    	System.out.println("----------------------------------------------");
    	System.out.println("-------------View All Orders---------------");
    	System.out.println();
        List<Order> allOrders = orderService.getAllOrders(restaurant);
        print.printOrders(allOrders, scanner );
    }
}
