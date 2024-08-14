package com.QuickBite.Print;

import java.util.List;
import java.util.Scanner;

import com.QuickBite.Inputs.Inputs;
import com.QuickBite.model.Address;
import com.QuickBite.model.Menu;
import com.QuickBite.model.Order;
import com.QuickBite.model.OrderDetail;
import com.QuickBite.model.PaymentMethod;
import com.QuickBite.model.Restaurant;
import com.QuickBite.model.Review;
import com.QuickBite.service.OrderService;


public class Print {
	private final Inputs input = new Inputs();

	public void printRestaurants(List<Restaurant> restaurants) {
	    System.out.println("\n========================================= Restaurants =====================================================");
	    System.out.printf("%-5s | %-20s | %-30s | %-15s | %-20s | %-6s%n", 
	                      "ID", "Name", "Address", "Cuisine Type", "Opening Hours", "Rating");
	    System.out.println("------|----------------------|--------------------------------|-----------------|----------------------|------");

	    for (Restaurant r : restaurants) {
	        System.out.printf("%-5d | %-20s | %-30s | %-15s | %-20s | %-6.1f%n",
	                          r.getRestaurantId(), 
	                          r.getName(), 
	                          r.getAddress(), 
	                          r.getCuisineType() != null ? r.getCuisineType() : "N/A",
	                          r.getOpeningHours() != null ? r.getOpeningHours() : "N/A",
	                          r.getRating() != null ? r.getRating() : 0.0);
	    }
	}

	public void printMenuItems(List<Menu> menuItems) {
	    System.out.println("\n============= Menu Items ===============");
	    System.out.printf("%-5s | %-20s | %-15s | %-66s | %-10s%n", "ID", "Name", "Category", "Description", "Price");
	    System.out.println("------|----------------------|-----------------|--------------------------------------------------------------------|----------");
	    
	    for (Menu m : menuItems) {
	        
	        System.out.printf("%-5d | %-20s | %-15s | %-66s | $%-9.2f%n",
	                m.getMenuId(),
	                m.getItemName(),
	                (m.getCategory() != null ? m.getCategory() : "N/A"),
	                (m.getDescription() != null ? m.getDescription() : "N/A"),
	                m.getPrice());
	    }
	}


    public void printReviews(List<Review> reviews) {
        System.out.println("\n============== Reviews ================");
        System.out.printf("%-5s | %-7s | %-30s%n", "ID", "Rating", "Comment");
        System.out.println("-----|--------|--------------------------------");
        for (Review r : reviews) {
            System.out.printf("%-5d | %-6d | %-30s%n", r.getReviewId(), r.getRating(), r.getComment());
        }
    }

    public void printPaymentMethods(List<PaymentMethod> paymentMethods) {
        System.out.println("\n============ Payment Methods =============");
        System.out.printf("%-5s | %-20s | %-15s%n", "ID", "Card Number", "Card Type");
        System.out.println("-----|----------------------|---------------");
        for (PaymentMethod pm : paymentMethods) {
            System.out.printf("%-5d | %-20s | %-15s%n", pm.getPaymentMethodId(), pm.getCardNumber(), pm.getCardType());
        }
    }

    public void printOrders(List<Order> orders, Scanner scanner , OrderService orderService) {
        System.out.println("\n=============== Orders ==================");
        System.out.printf("%-5s | %-20s | %-15s | %-10s | %-8s | %-15s%n", "ID", "Restaurant", "Date", "Amount", "Status", "Payment Mode");
        System.out.println("------|----------------------|-------------------------|------------|--------|-----------------");
        for (Order o : orders) {
            System.out.printf("%-5d | %-20s | %-15s | $%-9.2f | %-6s | %-15s%n",
                    o.getOrderId(),
                    o.getRestaurant().getName(),
                    o.getOrderDate(),
                    o.getTotalAmount(),
                    o.getStatus(),
                    o.getPaymentMode()); 
        }

        System.out.println();
        System.out.print("Enter the Order ID to view details (or 0 to exit): ");
        Long orderId = input.getLongInput(scanner);

        if (orderId == 0) {
            return;
        }

        Order selectedOrder = orders.stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .findFirst()
                .orElse(null);

        if (selectedOrder == null) {
            System.out.println("Order not found.");
            return;
        }

        printOrderDetails(selectedOrder);
        System.out.println();
        System.out.print("Is order dilivered (enter 1 to confrim):");
        int confirm = scanner.nextInt();
        if(confirm == 1) {
        	try {
                orderService.markOrderAsDelivered(orderId);
                System.out.println("Order marked as delivered successfully.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } 
        printOrders(orders , scanner , orderService);
    }

    public void printOrders(List<Order> orders, Scanner scanner) {
        // Print the list of orders
        System.out.println("\n=============== Orders ==================");
        System.out.printf("%-5s | %-20s | %-15s | %-10s | %-8s%n", "ID", "Restaurant", "Date", "Amount", "Status");
        System.out.println("-----|----------------------|-----------------|------------|--------");
        for (Order o : orders) {
            System.out.printf("%-5d | %-20s | %-15s | $%-9.2f | %-6s%n",
                    o.getOrderId(),
                    o.getRestaurant().getName(),
                    o.getOrderDate(),
                    o.getTotalAmount(),
                    o.getStatus());
        }

        // Ask the user to select an order to view details
        System.out.println();
        System.out.print("Enter the Order ID to view details (or 0 to exit): ");
        Long orderId = input.getLongInput(scanner);

        if (orderId == 0) {
            return; // Exit if user inputs 0
        }

        // Find the selected order
        Order selectedOrder = orders.stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .findFirst()
                .orElse(null);

        if (selectedOrder == null) {
            System.out.println("Order not found.");
            return;
        }

        // Print the details of the selected order
        printOrderDetails(selectedOrder);
    }
    public void printOrderDetails(Order order) {
        System.out.println("\n=============== Order Details ============");
        System.out.printf("Order ID: %d%n", order.getOrderId());
        System.out.printf("Restaurant: %s%n", order.getRestaurant().getName());
        System.out.printf("Order Date: %s%n", order.getOrderDate());
        System.out.printf("Total Amount: $%.2f%n", order.getTotalAmount());
        System.out.printf("Status: %s%n", order.getStatus());
        System.out.printf("Payment Mode: %s%n", order.getPaymentMode()); 
        System.out.println("Order Details:");
        System.out.printf("%-5s | %-20s | %-10s | %-10s | %-10s%n", "ID", "Item Name", "Qty", "Unit Price", "Subtotal");
        System.out.println("------|----------------------|----------|------------|----------");

        for (OrderDetail detail : order.getOrderDetails()) {
            System.out.printf("%-5d | %-20s | %-10d | $%-9.2f | $%-9.2f%n",
                    detail.getMenu().getMenuId(),
                    detail.getMenu().getItemName(),
                    detail.getQuantity(),
                    detail.getMenu().getPrice(),
                    detail.getSubtotal());
        }
    }


    public void printAddresses(List<Address> addresses) {
        System.out.println("\n============= Addresses ===============");
        System.out.printf("%-5s | %-30s | %-20s | %-15s | %-10s%n", "ID", "Street", "City", "State", "Zip Code");
        System.out.println("------|--------------------------------|----------------------|-----------------|----------");
        for (Address a : addresses) {
            System.out.printf("%-5d | %-30s | %-20s | %-15s | %-10s%n", a.getAddressId(), a.getStreet(), a.getCity(), a.getState(), a.getZipCode());
        }
    }
    public void printOrderSummary(Order order) {
        System.out.println("\n===================== Order Summary =========================");
        System.out.println();
        System.out.printf("Order ID: %d%n", order.getOrderId());
        System.out.printf("Restaurant: %s%n", order.getRestaurant().getName());
        System.out.printf("Order Date: %s%n", order.getOrderDate());
        System.out.printf("Total Amount: $%.2f%n", order.getTotalAmount());
        System.out.println();
        System.out.println("Order Details:");
        System.out.printf("%-5s | %-20s | %-10s | %-10s | %-10s%n", "ID", "Item Name", "Qty", "Unit Price", "Subtotal");
        System.out.println("------|----------------------|----------|------------|----------");

        for (OrderDetail detail : order.getOrderDetails()) {
            System.out.printf("%-5d | %-20s | %-10d | $%-9.2f | $%-9.2f%n",
                    detail.getMenu().getMenuId(),
                    detail.getMenu().getItemName(),
                    detail.getQuantity(),
                    detail.getMenu().getPrice(),
                    detail.getSubtotal());
        }
    }
}
