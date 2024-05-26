package AidAtlas;

import java.util.Scanner;

import static AidAtlas.MainNavigation.viewOpportunitiesForLoggedVolunteer;

public interface ShowVolunteerMenu {
    // Corrected method name to follow Java conventions
    static void showVolunteerMenu(Scanner scanner, Volunteer volunteer, ProfileManagement profileManagement) {
        while (true) {
            System.out.println("\nVolunteer Menu");
            System.out.println("1. View Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. View Matching Opportunities");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    volunteer.ViewProfile(); // Corrected method name to match Volunteer method
                    break;
                case 2:
                    volunteer.editProfile(); // Corrected method name to match Volunteer method
                    break;
                case 3:
                    viewOpportunitiesForLoggedVolunteer(volunteer, profileManagement);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    MainNavigation.clearConsole();
                    return;
                default:
                    System.out.println("Invalid option! Please choose again.");
            }
        }
    }
}
