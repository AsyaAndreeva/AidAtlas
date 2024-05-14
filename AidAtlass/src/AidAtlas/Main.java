package AidAtlas;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AuthenticationService authService = new AuthenticationService();
        ProfileManagement profileManagement = new ProfileManagement(authService);

        MainNavigation mainNavigation = new MainNavigation(profileManagement, scanner);
        mainNavigation.handleMainMenu();

        scanner.close(); // Close the scanner when done
    }

    // Your other methods remain the same
}
