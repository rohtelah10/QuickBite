package com.QuickBite.menu;

public class AllRestaurantMenu {

    public void printRestaurantManagementMenu() {
    	System.out.println();
    	System.out.println("----------------------------------------------");
        System.out.println("\n=== Restaurant Management ===");
        System.out.println("1. Open New Restaurant");
        System.out.println("2. Login As Existing Restaurant");
        System.out.println("3. Back to Main Menu");
        System.out.println();
        System.out.print("Enter your choice: ");
    }

    public void printManageRestaurantMenu() {
    	System.out.println();
    	System.out.println("----------------------------------------------");
        System.out.println("\n=== Manage Restaurant ===");
        System.out.println("1. Add Menu Item");
        System.out.println("2. Remove Menu Item");
        System.out.println("3. View All Reviews");
        System.out.println("4. Manage Orders");
        System.out.println("5. Exit Management");
        System.out.println();
        System.out.print("Enter your choice: ");
    }
}
