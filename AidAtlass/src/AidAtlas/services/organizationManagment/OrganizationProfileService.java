package AidAtlas.services.organizationManagment;

import AidAtlas.data.Organization;
import AidAtlas.data.Volunteer;
import AidAtlas.data.VolunteerOpportunities;

import java.util.Scanner;

public class OrganizationProfileService implements CreateProfileOrganization, EditProfileOrganization, ViewProfileOrganization {
    private final Scanner scanner;

    public OrganizationProfileService(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void createProfileOrganization(Organization organization) {
        System.out.println("Creating organization profile...");
        viewProfileDetails(organization);
    }

    @Override
    public void editProfileOrganization(Organization organization) {
        System.out.println("What would you like to edit?");
        System.out.println("1. Mission");
        System.out.println("2. Location");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character after reading integer

        switch (choice) {
            case 1:
                System.out.println("Enter new mission:");
                String newMission = scanner.nextLine();
                organization.setMission(newMission);
                break;
            case 2:
                System.out.println("Enter new location:");
                String newLocation = scanner.nextLine();
                organization.setLocation(newLocation);
                break;
        }
        System.out.println("Profile updated successfully.");
    }

    public void viewProfileOrganization(Organization organization) {
        System.out.println("Viewing organization profile...");
        viewProfileDetails(organization);
    }

    private void viewProfileDetails(Organization organization) {
        System.out.println("Organization name: " + organization.getName());
        System.out.println("Mission: " + organization.getMission());
        System.out.println("Location: " + organization.getLocation());
        System.out.println("Volunteer opportunities: ");
        for (VolunteerOpportunities opportunity : organization.getVolunteerOpportunities()) {
            System.out.println("- Opportunity Name: " + opportunity.getOpportunityName());
            System.out.println("  Location: " + opportunity.getLocation());
            System.out.println("  Required Skills: " + opportunity.getRequiredSkills());
            System.out.println("  Required Weekly Hours: " + opportunity.getRequiredWeeklyHours());
            System.out.println("  Required Number of Volunteers: " + VolunteerOpportunities.getRequiredNumberOfVolunteers());
            System.out.println(); // Add a blank line for separation
        }
        System.out.println("Volunteers: ");
        for (Volunteer volunteer : organization.getVolunteers()) {
            System.out.println("- " + volunteer.getName());
        }
    }
}
