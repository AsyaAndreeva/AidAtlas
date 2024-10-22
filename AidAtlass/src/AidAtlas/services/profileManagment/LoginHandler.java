package AidAtlas.services.profileManagment;

import AidAtlas.data.User;
import AidAtlas.services.profileManagment.ProfileManagement;
import AidAtlas.services.profileManagment.RoleSpecificNavigator;

import java.util.Scanner;

public class LoginHandler {

    public LoginHandler(ProfileManagement profileManagement) {
    }

    public static void loginUser(Scanner scanner, ProfileManagement profileManagement) {
        try {
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            User user = profileManagement.login(email, password);
            if (user != null) {
                System.out.println("Login successful!");
                RoleSpecificNavigator.navigateToRoleSpecificMenu(scanner, profileManagement, user, user.getRole());
            } else {
                System.out.println("Invalid email or password.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred during login: " + e.getMessage());
        }
    }
}
