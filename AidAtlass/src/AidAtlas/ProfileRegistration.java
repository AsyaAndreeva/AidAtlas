package AidAtlas;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static AidAtlas.MainNavigation.viewOpportunitiesForLoggedVolunteer;

public class ProfileRegistration implements ShowOrganizationMenu, ShowVolunteerMenu{

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
    public static void registerUser(Scanner scanner, ProfileManagement profileManagement) {
        String name = promptForName(scanner);
        String email = promptForEmail(scanner);
        String password = promptForPassword(scanner);
        UserRole role = promptForUserRole(scanner);

        if (role != null) {
            processUserRegistration(scanner, profileManagement, name, email, password, role);
        } else {
            System.out.println("Invalid role! Please choose VOLUNTEER or ORGANIZATION.");
        }
    }

    private static String promptForName(Scanner scanner) {
        System.out.print("Enter your name: ");
        return scanner.nextLine();
    }

    private static String promptForEmail(Scanner scanner) {
        String email;
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher;
        do {
            System.out.print("Enter your email: ");
            email = scanner.nextLine();
            matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                System.out.println("Invalid email format! Please enter a valid email.");
            }
        } while (!matcher.matches());
        return email;
    }

    private static String promptForPassword(Scanner scanner) {
        String password;
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher;
        do {
            System.out.print("Enter your password: ");
            password = scanner.nextLine();
            matcher = pattern.matcher(password);
            if (!matcher.matches()) {
                System.out.println("Invalid password format! Password must contain at least 8 characters, including one uppercase letter, one lowercase letter, and one digit.");
            }
        } while (!matcher.matches());
        return password;
    }


    private static UserRole promptForUserRole(Scanner scanner) {
        System.out.print("Are you registering as a volunteer or organization? (VOLUNTEER/ORGANIZATION): ");
        String roleInput = scanner.nextLine().toUpperCase();
        try {
            return UserRole.valueOf(roleInput);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static void processUserRegistration(Scanner scanner, ProfileManagement profileManagement, String name, String email, String password, UserRole role) {
        User newUser = profileManagement.createProfile(name, email, password, role);
        if (newUser != null) {
            System.out.println("Registration successful!");
            navigateToRoleSpecificMenu(scanner, profileManagement, newUser, role);
        } else {
            System.out.println("Registration failed. Please try again.");
        }
    }

    static void navigateToRoleSpecificMenu(Scanner scanner, ProfileManagement profileManagement, User newUser, UserRole role) {
        if (role == UserRole.VOLUNTEER) {
            MainNavigation mainNav = new MainNavigation(profileManagement, scanner); // Removed unnecessary casting
            ShowVolunteerMenu.showVolunteerMenu(scanner, (Volunteer) newUser, profileManagement); // Corrected method name
        } else if (role == UserRole.ORGANIZATION) {
            MainNavigation mainNav = new MainNavigation(profileManagement, scanner); // Removed unnecessary casting
            ShowOrganizationMenu.showOrganizationMenu(scanner, (Organization) newUser, profileManagement);
        }
    }


}
