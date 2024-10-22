package AidAtlas.services.navigation;

import AidAtlas.data.Volunteer;
import AidAtlas.services.matching.MatchingEngine;
import AidAtlas.services.opportunityManagment.OpportunityFetcher;
import AidAtlas.services.opportunityManagment.OpportunitySorter;
import AidAtlas.services.opportunityManagment.OpportunityViewer;
import AidAtlas.services.profileManagment.ProfileManagement;
import AidAtlas.services.volunteerManagment.VolunteerProfileService;

import java.util.Scanner;

public interface ShowVolunteerMenu {
    // Corrected method name to follow Java conventions
    static void showVolunteerMenu(Scanner scanner, Volunteer volunteer, ProfileManagement profileManagement) {
        VolunteerProfileService volunteerProfileService = new VolunteerProfileService(scanner);
        MatchingEngine matchingEngine = new MatchingEngine();
        OpportunityFetcher opportunityFetcher = new OpportunityFetcher(profileManagement);
        OpportunitySorter opportunitySorter = new OpportunitySorter();
        OpportunityViewer opportunityViewer = new OpportunityViewer(matchingEngine, opportunityFetcher, opportunitySorter);
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
                    volunteerProfileService.viewProfileVolunteer(volunteer); // Calling non-static method on the instance
                    break;
                case 2:
                    volunteerProfileService.editProfileVolunteer(volunteer); // Corrected method name to match Volunteer method
                    break;
                case 3:
                    opportunityViewer.viewOpportunitiesForLoggedVolunteer(volunteer);
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
