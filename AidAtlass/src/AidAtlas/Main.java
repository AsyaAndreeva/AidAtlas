package AidAtlas;

import AidAtlas.data.InMemoryUserStorage;
import AidAtlas.data.UserStorage;
import AidAtlas.fileStorage.FileUserStorage;
import AidAtlas.services.profileManagment.AuthenticationService;
import AidAtlas.services.navigation.MainNavigation;
import AidAtlas.services.profileManagment.ProfileManagement;
import AidAtlas.services.profileManagment.UserRegistrationService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize UserStorage and other services
        UserStorage userStorage = new InMemoryUserStorage();
        AuthenticationService authService = new AuthenticationService(userStorage);
        UserRegistrationService userRegistrationService = new UserRegistrationService(userStorage);
        ProfileManagement profileManagement = new ProfileManagement(authService, userRegistrationService);

        // Initialize main navigation
        MainNavigation mainNavigation = new MainNavigation(profileManagement, scanner);

        // Start the application
        mainNavigation.handleMainMenu();

        scanner.close(); // Close the scanner when done
    }
}
