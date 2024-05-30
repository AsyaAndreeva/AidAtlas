package AidAtlas.services.profileManagment;

import AidAtlas.data.Organization;
import AidAtlas.data.User;
import AidAtlas.data.UserRole;
import AidAtlas.data.Volunteer;
import AidAtlas.services.navigation.MainNavigation;
import AidAtlas.services.navigation.ShowOrganizationMenu;
import AidAtlas.services.navigation.ShowVolunteerMenu;

import java.util.Scanner;

public class ProfileRegistration {

    public static void registerUser(Scanner scanner, ProfileManagement profileManagement) {
        UserPrompter userPrompter = new UserPrompter(scanner);
        InputValidator inputValidator = new InputValidator();

        String name = userPrompter.promptForName();
        String email = userPrompter.promptForEmail(inputValidator);
        String password = userPrompter.promptForPassword(inputValidator);
        UserRole role = userPrompter.promptForUserRole();

        if (role != null) {
            processUserRegistration(scanner, profileManagement, name, email, password, role);
        } else {
            System.out.println("Invalid role! Please choose VOLUNTEER or ORGANIZATION.");
        }
    }

    private static void processUserRegistration(Scanner scanner, ProfileManagement profileManagement, String name, String email, String password, UserRole role) {
        User newUser = profileManagement.createProfile(name, email, password, role);
        if (newUser != null) {
            System.out.println("Registration successful!");
            RoleSpecificNavigator.navigateToRoleSpecificMenu(scanner, profileManagement, newUser, role);
        } else {
            System.out.println("Registration failed. Please try again.");
        }
    }
}
