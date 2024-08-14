package com.QuickBite;

import com.QuickBite.Inputs.Inputs;
import com.QuickBite.menu.MainMenu;
import com.QuickBite.service.FeedbackService;

import java.util.Scanner;

public class QuickBiteApp {
    private final MainMenu mainMenu;
    private final UserManagement userObject;
    private final RestaurantManagement restaurantObject;
    private final Inputs input;
    private final FeedbackService feedbackService;

    public QuickBiteApp() {
        // Initialize objects
        userObject = new UserManagement();
        restaurantObject = new RestaurantManagement();
        input = new Inputs();
        mainMenu = new MainMenu();
        feedbackService = new FeedbackService();
    }

    public void startApplication() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            mainMenu.printMainMenu();
            int choice = input.getIntInput(scanner);

            switch (choice) {
                case 1:
                    userObject.handleUserManagement(scanner);
                    break;
                case 2:
                    restaurantObject.handleRestaurantManagement(scanner);
                    break;
                case 3:
                    exit = true;
                    handleFeedbackBeforeExit(scanner);
                    System.out.println();
                    System.out.println("-------------Exiting application.-------------");
                    System.out.println("----------------------------------------------");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private void handleFeedbackBeforeExit(Scanner scanner) {
    	scanner.nextLine();
        System.out.print("Would you like to provide feedback before exiting? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("yes")) {
            collectFeedback(scanner);
        } else if (!response.equals("no")) {
            System.out.println("Invalid response. Feedback will not be collected.");
        }
    }

    private void collectFeedback(Scanner scanner) {
        System.out.print("Please enter your name: ");
        String userName = scanner.nextLine().trim();
        System.out.print("Please enter your feedback/comments: ");
        String comments = scanner.nextLine().trim();
        
        feedbackService.submitFeedback(userName, comments);
        System.out.println("Thank you for your feedback!");
    }

    public static void main(String[] args) {
        QuickBiteApp app = new QuickBiteApp();
        app.startApplication();
    }
}
