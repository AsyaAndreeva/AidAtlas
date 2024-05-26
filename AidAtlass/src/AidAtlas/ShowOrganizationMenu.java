package AidAtlas;

import java.util.Scanner;

public interface ShowOrganizationMenu {
    static void showOrganizationMenu(Scanner scanner, Organization organization, ProfileManagement profileManagement) {
        while (true) {
            System.out.println("\nOrganization Menu");
            System.out.println("1. View Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. Create Opportunity");
            System.out.println("4. View Matched Volunteers");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    organization.ViewProfile();
                    break;
                case 2:
                    organization.editProfile();
                    break;
                case 3:
                    MainNavigation.createOpportunity(scanner, organization, profileManagement);
                    break;
                case 4:
                    MainNavigation.viewVolunteersForLoggedOrganization(organization, profileManagement);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    MainNavigation.clearConsole();
                    return;
                default:
                    System.out.println("Invalid option! Please choose again.");
            }
        }
    }
}
