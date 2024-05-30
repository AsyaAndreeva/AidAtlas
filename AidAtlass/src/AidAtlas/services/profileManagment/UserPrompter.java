package AidAtlas.services.profileManagment;

import AidAtlas.data.UserRole;
import AidAtlas.services.profileManagment.InputValidator;

import java.util.Scanner;

public class UserPrompter {
    private final Scanner scanner;

    public UserPrompter(Scanner scanner) {
        this.scanner = scanner;
    }

    public String promptForName() {
        System.out.print("Enter your name: ");
        return scanner.nextLine();
    }

    public String promptForEmail(InputValidator validator) {
        String email;
        do {
            System.out.print("Enter your email: ");
            email = scanner.nextLine();
            if (!validator.isValidEmail(email)) {
                System.out.println("Invalid email format! Please enter a valid email.");
            }
        } while (!validator.isValidEmail(email));
        return email;
    }

    public String promptForPassword(InputValidator validator) {
        String password;
        do {
            System.out.print("Enter your password: ");
            password = scanner.nextLine();
            if (!validator.isValidPassword(password)) {
                System.out.println("Invalid password format! Password must contain at least 8 characters, including one uppercase letter, one lowercase letter, and one digit.");
            }
        } while (!validator.isValidPassword(password));
        return password;
    }

    public UserRole promptForUserRole() {
        System.out.print("Are you registering as a volunteer or organization? (VOLUNTEER/ORGANIZATION): ");
        String roleInput = scanner.nextLine().toUpperCase();
        try {
            return UserRole.valueOf(roleInput);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
