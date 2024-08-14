package com.QuickBite.menu;

public class AllUserMenu {

    public void printUserManagementMenu() {
    	System.out.println();
    	System.out.println("----------------------------------------------");
        System.out.println("\n=== User Management ===");
        System.out.println("1. Create User");
        System.out.println("2. Login User");
        System.out.println();
        System.out.print("Enter your choice: ");
    }

    public void printUserActionsMenu() {
        System.out.println("----------------------------------------------");
        System.out.println("\n=== User Actions ===");
        System.out.println("1. Manage Orders");
        System.out.println("2. Manage Addresses");
        System.out.println("3. Manage Payment Methods");
        System.out.println("4. Edit User Details");
        System.out.println("5. Add Review");
        System.out.println("6. Logout");
        System.out.println();
        System.out.print("Enter your choice: ");
    }

}
