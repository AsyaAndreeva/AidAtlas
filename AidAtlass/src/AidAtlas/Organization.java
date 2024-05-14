package AidAtlas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Organization extends User implements CreateProfile, EditProfile, ViewProfile {

    private String mission;
    private String location;
    private List<VolunteerOpportunities> volunteerOpportunities; // List of volunteer opportunities available
    private List<Volunteer> volunteers; // List of volunteers associated with the organization


    public Organization(String name, String email, String password,  String mission, String location, List<VolunteerOpportunities> volunteerOpportunities, List<Volunteer> volunteers) {
        super(name, email, password, UserRole.ORGANIZATION);
        this.mission = mission;
        this.location = location;
        this.volunteerOpportunities = (volunteerOpportunities != null) ? volunteerOpportunities : new ArrayList<>(); // Initialize if null
        this.volunteers = (volunteers != null) ? volunteers : new ArrayList<>(); // Initialize if null
    }


    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<VolunteerOpportunities> getVolunteerOpportunities() {
        return volunteerOpportunities;
    }

    public void setVolunteerOpportunities(List<VolunteerOpportunities> volunteerOpportunities) {
        this.volunteerOpportunities = volunteerOpportunities;
    }

    public List<Volunteer> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(List<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }

    @Override
    public void createProfile() {
        System.out.println("Creating volunteer profile...");
        ViewProfileDetails();
    }

    @Override
    public void editProfile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like to edit?");
        System.out.println("1. Mission");
        System.out.println("2. Location");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character after reading integer

        switch (choice) {
            case 1:
                System.out.println("Enter new mission");
                String newMission = scanner.nextLine();
                setMission(newMission);
                break;
            case 2:
                System.out.println("Enter new Location");
                String newLocation = scanner.nextLine();
                setLocation(newLocation);
                break;
        }
        System.out.println("Profile updated successfully.");
    }

    @Override
    public void ViewProfile() {
        System.out.println("Viewing organization profile...");
        ViewProfileDetails();
    }

    private void ViewProfileDetails() {
        System.out.println("Organization name: " + getName());
        System.out.println("Mission: " + getMission());
        System.out.println("Location: " + getLocation());
        System.out.println("Volunteer opportunities: ");
        for (VolunteerOpportunities opportunity : volunteerOpportunities) {
            System.out.println("- Opportunity Name: " + opportunity.getOppurtunityName());
            System.out.println("  Location: " + opportunity.getLocation());
            System.out.println("  Required Skills: " + opportunity.getRequiredSkills());
            System.out.println("  Required Weekly Hours: " + opportunity.getRequiredWeeklyHours());
            System.out.println("  Required Number of Volunteers: " + opportunity.getRequiredNumberOfVolunteers());
            System.out.println(); // Add a blank line for separation
        }
        System.out.println("Volunteers: ");
        for (Volunteer volunteer : volunteers) {
            System.out.println("- " + volunteer.getName());
        }
    }


}
