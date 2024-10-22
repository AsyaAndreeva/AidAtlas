package AidAtlas.services.navigation;
import AidAtlas.services.profileManagment.LoginHandler;
import AidAtlas.services.profileManagment.ProfileManagement;
import AidAtlas.services.profileManagment.ProfileRegistration;

import java.util.*;

public class MainNavigation {
    private static ProfileManagement profileManagement;

    private final Scanner scanner;

    public MainNavigation(ProfileManagement profileManagement, Scanner scanner) {
        MainNavigation.profileManagement = profileManagement;
        this.scanner = scanner;
    }

    public void displayMainMenu() {
        System.out.println("\nWelcome to AidAtlas!");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
    }

    public void handleMainMenu() {
        while (true) {
            try {
                displayMainMenu();
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        clearConsole();
                        ProfileRegistration.registerUser(scanner, profileManagement);
                        break;
                    case 2:
                        clearConsole();
                        LoginHandler.loginUser(scanner, profileManagement);
                        break;
                    case 3:
                        clearConsole();
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option! Please choose again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    static void clearConsole() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }

}