package com.QuickBite;

import java.util.Date;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import com.QuickBite.Inputs.Inputs;
import com.QuickBite.Print.Print;
import com.QuickBite.dao.AddressDAO;
import com.QuickBite.dao.MenuDAO;
import com.QuickBite.dao.OrderDAO;
import com.QuickBite.dao.PaymentMethodDAO;
import com.QuickBite.dao.RestaurantDAO;
import com.QuickBite.dao.ReviewDAO;
import com.QuickBite.dao.UserDAO;
import com.QuickBite.daoImpl.AddressDAOImpl;
import com.QuickBite.daoImpl.MenuDAOImpl;
import com.QuickBite.daoImpl.OrderDAOImpl;
import com.QuickBite.daoImpl.PaymentMethodDAOImpl;
import com.QuickBite.daoImpl.RestaurantDAOImpl;
import com.QuickBite.daoImpl.ReviewDAOImpl;
import com.QuickBite.daoImpl.UserDAOImpl;
import com.QuickBite.menu.AllUserMenu;
import com.QuickBite.model.Address;
import com.QuickBite.model.Menu;
import com.QuickBite.model.Order;
import com.QuickBite.model.OrderDetail;
import com.QuickBite.model.PaymentMethod;
import com.QuickBite.model.Restaurant;
import com.QuickBite.model.Review;
import com.QuickBite.model.User;
import com.QuickBite.service.AddressService;
import com.QuickBite.service.MenuService;
import com.QuickBite.service.OrderService;
import com.QuickBite.service.PaymentMethodService;
import com.QuickBite.service.RestaurantService;
import com.QuickBite.service.ReviewService;
import com.QuickBite.service.UserService;
import com.QuickBite.service.impl.MenuServiceImpl;
import com.QuickBite.service.impl.RestaurantServiceImpl;
import com.QuickBite.service.impl.ReviewServiceImpl;

public class UserManagement {
	private final UserService userService;
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;
    private final PaymentMethodService paymentMethodService;
    private final MenuService menuService;
    private final OrderService orderService;
    private final AddressService addressService;
    private final AllUserMenu userMenu;
    private final Inputs input;
    private final Print print;
    private User currentUser;


	public UserManagement() {
		super();
		UserDAO userDAO = new UserDAOImpl();
        RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
        ReviewDAO reviewDAO = new ReviewDAOImpl();
        PaymentMethodDAO paymentMethodDAO = new PaymentMethodDAOImpl();
        MenuDAO menuDAO = new MenuDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        AddressDAO addressDAO = new AddressDAOImpl();

        // Initialize services with DAOs
        userService = new UserService(userDAO);
        restaurantService = new RestaurantServiceImpl(restaurantDAO);
        reviewService = new ReviewServiceImpl(reviewDAO);
        paymentMethodService = new PaymentMethodService(paymentMethodDAO);
        menuService = new MenuServiceImpl(menuDAO);
        orderService = new OrderService(orderDAO);
        addressService = new AddressService(addressDAO);
        
        //menus
        userMenu = new AllUserMenu();
        
        //Input functions
		input = new Inputs();
		
		//Print Functions
		print = new Print();
	}
	
	public void handleUserManagement(Scanner scanner) {
        userMenu.printUserManagementMenu();
        int choice = input.getIntInput(scanner);

        switch (choice) {
            case 1:
                createUser(scanner);
                break;
            case 2:
                loginUser(scanner);
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
        }
    }


	public void createUser(Scanner scanner) {
	    scanner.nextLine();
	    System.out.println();
	    System.out.println("----------------------------------------------------------");
	    System.out.println("Please provide the following details to create a new user:");

	    System.out.print("Enter username (required): ");
	    String username = scanner.nextLine().trim();
	    if (username.isEmpty()) {
	        System.out.println("Error: Username cannot be empty.");
	        return;
	    }
	    if (username.length() < 3 || username.length() > 20) {
	        System.out.println("Error: Username must be between 3 and 20 characters.");
	        return;
	    }

	    System.out.print("Enter password (required): ");
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

	    System.out.print("Enter email (required): ");
	    String email = scanner.nextLine().trim();
	    if (email.isEmpty()) {
	        System.out.println("Error: Email cannot be empty.");
	        return;
	    }
	    if (!Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$").matcher(email).matches()) {
	        System.out.println("Error: Invalid email format.");
	        return;
	    }

	    System.out.print("Enter phone number (required): ");
	    String phone = scanner.nextLine().trim();
	    if (phone.isEmpty()) {
	        System.out.println("Error: Phone number cannot be empty.");
	        return;
	    }

	    if (!Pattern.compile("\\d{10}").matcher(phone).matches()) {
	        System.out.println("Error: Phone number must be exactly 10 digits.");
	        return;
	    }

	    User newUser = new User(username, email, password, phone);
	    userService.createUser(newUser);

	    System.out.println("User created successfully.");
	    handleUserManagement(scanner);
	}


	public void loginUser(Scanner scanner) {
	    scanner.nextLine();
	    System.out.println();
	    System.out.println("----------------------------------------------");
	    System.out.println("----------------Login User--------------------");
	    System.out.println();
	   
	    System.out.print("Enter username: ");
	    String username = scanner.nextLine().trim();
	    
	    
	    System.out.print("Enter password: ");
	    String password = scanner.nextLine().trim();
  
	    User user = userService.findByUsername(username);
	    
	    if (user != null && user.getPassword().equals(password)) {
	        currentUser = user;
	        System.out.println("Logged in as " + username);
	        handleUserActions(scanner);
	    } else {
	        System.out.println("Invalid username or password.");
	    }
	}


	public void handleUserActions(Scanner scanner) {
	    boolean exitActions = false;
	    while (!exitActions) {
	        String name = currentUser.getUsername();
	        System.out.println();
	        System.out.println("--------------- " + name + " ----------------");
	        userMenu.printUserActionsMenu();
	        int choice = input.getIntInput(scanner);

	        switch (choice) {
	            case 1:
	                manageOrders(scanner);
	                break;
	            case 2:
	                manageAddresses(scanner);
	                break;
	            case 3:
	                managePaymentMethods(scanner);
	                break;
	            case 4:
	                editUserDetails(scanner, currentUser);
	                break;
	            case 5:
	                addReview(scanner, currentUser);
	                break;
	            case 6:
	                exitActions = true;
	                currentUser = null; // Log out user
	                System.out.println("Logged out.");
	                break;
	            default:
	                System.out.println("Invalid choice. Please enter a valid option.");
	        }
	    }
	}
	private void manageOrders(Scanner scanner) {
	    boolean exitOrders = false;
	    while (!exitOrders) {
	        System.out.println();
	        System.out.println("----- Manage Orders -----");
	        System.out.println("1. Place Order");
	        System.out.println("2. View All Orders");
	        System.out.println("3. Exit");
	        System.out.print("Enter your choice: ");
	        int choice = input.getIntInput(scanner);

	        switch (choice) {
	            case 1:
	                placeOrder(scanner);
	                break;
	            case 2:
	                viewAllOrders(scanner);
	                break;
	            case 3:
	                exitOrders = true;
	                break;
	            default:
	                System.out.println("Invalid choice. Please enter a valid option.");
	        }
	    }
	}
	
	private void manageAddresses(Scanner scanner) {
	    boolean exitAddresses = false;
	    while (!exitAddresses) {
	        System.out.println();
	        System.out.println("----- Manage Addresses -----");
	        System.out.println("1. View All Addresses");
	        System.out.println("2. Add Address");
	        System.out.println("3. Remove Address");
	        System.out.println("4. Exit");
	        System.out.print("Enter your choice: ");
	        int choice = input.getIntInput(scanner);

	        switch (choice) {
	            case 1:
	                viewAllAddresses(scanner);
	                break;
	            case 2:
	                addAddress(scanner);
	                break;
	            case 3:
	                removeAddress(scanner);
	                break;
	            case 4:
	                exitAddresses = true;
	                break;
	            default:
	                System.out.println("Invalid choice. Please enter a valid option.");
	        }
	    }
	}
	
	private void managePaymentMethods(Scanner scanner) {
	    boolean exitPaymentMethods = false;
	    while (!exitPaymentMethods) {
	        System.out.println();
	        System.out.println("----- Manage Payment Methods -----");
	        System.out.println("1. View All Payment Methods");
	        System.out.println("2. Add Payment Method");
	        System.out.println("3. Remove Payment Method");
	        System.out.println("4. Exit");
	        System.out.print("Enter your choice: ");
	        int choice = input.getIntInput(scanner);

	        switch (choice) {
	            case 1:
	                viewAllPaymentMethods(scanner);
	                break;
	            case 2:
	                addPaymentMethod(scanner);
	                break;
	            case 3:
	                removePaymentMethod(scanner);
	                break;
	            case 4:
	                exitPaymentMethods = true;
	                break;
	            default:
	                System.out.println("Invalid choice. Please enter a valid option.");
	        }
	    }
	}




    public void placeOrder(Scanner scanner) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        print.printRestaurants(restaurants);
        System.out.println();
        System.out.print("Enter restaurant ID to place an order:");
        Long restaurantId = input.getLongInput(scanner);

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (restaurant == null) {
            System.out.println("Restaurant not found.");
            return;
        }
        viewAllAddresses(scanner);
        System.out.println();
        System.out.print("Want to add new address(enter 1) or continue (enter 0) :");
        int choice = scanner.nextInt();
        if(choice==1) {
        	addAddress(scanner);
        	viewAllAddresses(scanner);
        }
        System.out.println();
        System.out.print("Enter delivery address ID:");
        Long addressId = input.getLongInput(scanner);

        Address deliveryAddress = addressService.findById(addressId);
        if (deliveryAddress == null) {
            System.out.println("Address not found.");
            return;
        }

        List<Menu> menuItems = menuService.getMenuByRestaurant(restaurant);
        print.printMenuItems(menuItems);

        Set<OrderDetail> orderDetails = new HashSet<>();
        double totalAmount = 0.0;
        Order order = new Order();

        while (true) {
        	System.out.println();
            System.out.print("Enter menu item ID to add to order (or 0 to finish):");
            Long menuId = input.getLongInput(scanner);

            if (menuId == 0) {
                break;
            }

            Menu menuItem = menuItems.stream().filter(m -> m.getMenuId().equals(menuId)).findFirst().orElse(null);
            if (menuItem == null) {
                System.out.println("Menu item not found.");
                continue;
            }

            System.out.print("Enter quantity:");
            int quantity = input.getIntInput(scanner);

            double itemTotal = menuItem.getPrice() * quantity;
            totalAmount += itemTotal;

            OrderDetail orderDetail = new OrderDetail(order ,menuItem, quantity , itemTotal );
            orderDetails.add(orderDetail);            
        }

        System.out.println("\nSelect payment mode:");
        System.out.println("1. UPI");
        System.out.println("2. Card");
        System.out.println("3. Cash on Delivery");
        System.out.print("Enter your choice (1-3): ");
        int paymentModeChoice = input.getIntInput(scanner);
        String paymentMode;
        switch (paymentModeChoice) {
            case 1:
                paymentMode = "UPI";
                break;
            case 2:
                paymentMode = "Card";
                break;
            case 3:
                paymentMode = "Cash on Delivery";
                break;
            default:
                System.out.println("Invalid choice, defaulting to 'Cash on Delivery'.");
                paymentMode = "Cash on Delivery";
        }
        order.setUser(currentUser);
        order.setRestaurant(restaurant);
        order.setOrderDate(new Date());
        order.setTotalAmount(totalAmount);
        order.setStatus("Pending");
        order.setDeliveryAddress(deliveryAddress);
        order.setOrderDetails(orderDetails);
        order.setPaymentMode(paymentMode);

        orderService.createOrder(order);
        print.printOrderSummary(order);
        System.out.println();
        System.out.println("Order placed successfully.");
    }

    public void viewAllOrders(Scanner scanner) {
        List<Order> orders = orderService.getOrdersByUser(currentUser);
        print.printOrders(orders , scanner);
    }

    public void viewAllAddresses(Scanner scanner) {
        List<Address> addresses = addressService.findByUser(currentUser);
        print.printAddresses(addresses);
    }

    public void addAddress(Scanner scanner) {
    	scanner.nextLine();
    	System.out.println("----------------------------------------------");
    	System.out.println("--------------Add New Address-----------------");
    	System.out.println();
        System.out.print("Enter street:");
        String street = scanner.nextLine().trim();

        System.out.print("Enter city:");
        String city = scanner.nextLine().trim();

        System.out.print("Enter state:");
        String state = scanner.nextLine().trim();

        System.out.print("Enter zip code:");
        String zipCode = scanner.nextLine().trim();

        Address address = new Address(currentUser , street , city , state, zipCode);
        addressService.createAddress(address);
        System.out.println("Address added successfully.");
    }

    public void removeAddress(Scanner scanner) {
        List<Address> addresses = addressService.findByUser(currentUser);
        print.printAddresses(addresses);

        System.out.println("Enter address ID to remove:");
        Long addressId = input.getLongInput(scanner);

        Address addressToRemove = addressService.findById(addressId);
        if (addressToRemove != null) {
            addressService.deleteAddress(addressId);
            System.out.println("Address removed successfully.");
        } else {
            System.out.println("Address not found.");
        }
    }

    public void viewAllPaymentMethods(Scanner scanner) {
        List<PaymentMethod> paymentMethods = paymentMethodService.getPaymentMethodsByUser(currentUser);
        print.printPaymentMethods(paymentMethods);
    }

    public void addPaymentMethod(Scanner scanner) {
    	scanner.nextLine();
    	System.out.println("----------------------------------------------");
    	System.out.println("------------Add Payment Method----------------");
    	System.out.println();
        System.out.print("Enter card number:");
        String cardNumber = scanner.nextLine().trim();

        System.out.print("Enter expiry date (MM/YY):");
        String expiryDate = scanner.nextLine().trim();

        System.out.print("Enter card type:");
        String cardType = scanner.nextLine().trim();

        PaymentMethod paymentMethod = new PaymentMethod(currentUser,cardNumber,expiryDate,cardType);
        paymentMethodService.addPaymentMethod(paymentMethod);
        System.out.println("Payment method added successfully.");
    }

    public void removePaymentMethod(Scanner scanner) {
        List<PaymentMethod> paymentMethods = paymentMethodService.getPaymentMethodsByUser(currentUser);
        print.printPaymentMethods(paymentMethods);

        System.out.println("Enter payment method ID to remove:");
        Long paymentMethodId = input.getLongInput(scanner);

        PaymentMethod paymentMethodToRemove = paymentMethods.stream()
                .filter(pm -> pm.getPaymentMethodId().equals(paymentMethodId))
                .findFirst()
                .orElse(null);

        if (paymentMethodToRemove != null) {
            paymentMethodService.removePaymentMethod(paymentMethodToRemove);
            System.out.println("Payment method removed successfully.");
        } else {
            System.out.println("Payment method not found.");
        }
    }

    public void editUserDetails(Scanner scanner, User user) {
    	scanner.nextLine();
    	System.out.println("----------------------------------------------");
    	System.out.println("------------Edit User Details-----------------");
    	System.out.println();
        System.out.print("Enter new email (or press enter to skip):");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) {
            user.setEmail(email);
        }

        System.out.print("Enter new phone number (or press enter to skip):");
        String phone = scanner.nextLine().trim();
        if (!phone.isEmpty()) {
            user.setPhone(phone);
        }

        userService.updateUser(user);
        System.out.println("User details updated successfully.");
    }

    public void addReview(Scanner scanner, User user) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        print.printRestaurants(restaurants);
        System.out.println();
        System.out.print("Enter restaurant ID to review:");
        Long restaurantId = input.getLongInput(scanner);

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (restaurant == null) {
            System.out.println("Restaurant not found.");
            return;
        }

        System.out.print("Enter rating (1-5):");
        int rating = input.getIntInput(scanner);
        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating. Rating should be between 1 and 5.");
            return;
        }
        scanner.nextLine();
        System.out.print("Enter comment:");

        String comment = scanner.nextLine().trim();

        Review review = new Review();
        review.setRestaurant(restaurant);
        review.setUser(user);
        review.setRating(rating);
        review.setComment(comment);

        reviewService.createReview(review);
        System.out.println("Review added successfully.");
    }
}
