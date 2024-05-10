package AidAtlas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Navigation {
    private AidAtlasApp app;
    private InputHandler inputHandler;

    private MatchingEngine matchingEngine;

    public Navigation(AidAtlasApp app) {
        this.app = app;
        this.inputHandler = new InputHandler();
        this.matchingEngine = new MatchingEngine();
    }

    public void displayMainMenu() {
        System.out.println("\nWelcome to AidAtlas!");
        System.out.println("1. View Volunteers");
        System.out.println("2. View Organizations");
        System.out.println("3. View Volunteer opportunities");
        System.out.println("4. Add Volunteer");
        System.out.println("5. Add Organization");
        System.out.println("6. Add Volunteer opportunity");
        System.out.println("7. Match");
        System.out.println("8. Exit");
    }

    public void handleMainMenu() {
        boolean exit = false;
        while (!exit) {
            displayMainMenu();
            int choice = inputHandler.getIntInput("Choose an option");
            switch (choice) {
                case 1:
                    viewVolunteers();
                    break;
                case 2:
                    viewOrganizations();
                    break;
                case 3:
                    viewVolunteerOppurtunities();
                    break;
                case 4:
                    addVolunteer();
                    break;
                case 5:
                    addOrganization();
                    break;
                case 6:
                    addVolunteerOppurtunity();
                    break;
                case 7:
                    matchVolunteersToOrganizations();
                    break;
                case 8:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option! Please choose again.");
            }
        }
    }

    private void viewVolunteers() {
        System.out.println("\nVolunteers:");
        List<Volunteer> volunteers = app.getVolunteers();
        for (Volunteer volunteer : volunteers) {
            System.out.println(volunteer.getName());
            // You can display other details of the volunteer as well
        }
    }

    private void viewVolunteerOppurtunities() {
        System.out.println("\nVolunteer opportunities:");
        List<VolunteerOpportunities> volunteerOpportunities = app.getVolunteerOpportunities();
        for (VolunteerOpportunities volunteerOpportunity : volunteerOpportunities) {
            System.out.println(volunteerOpportunity.getOppurtunityName());
            // You can display other details of the volunteer as well
        }
    }

    private void viewOrganizations() {
        System.out.println("\nOrganizations:");
        List<Organization> organizations = app.getOrganizations();
        for (Organization organization : organizations) {
            System.out.println(organization.getName());
            // You can display other details of the organization as well
        }
    }

    private void addVolunteer() {
        System.out.println("\nEnter details for the new volunteer:");
        String name = inputHandler.getStringInput("Name");
        String email = inputHandler.getStringInput("Email");
        String password = inputHandler.getStringInput("Password");
        String location = inputHandler.getStringInput("location");
        // Get skills, interests, and preferred types of work
        List<String> skills = inputHandler.getListInput("Skills");

        // Get available hours weekly and total volunteered hours
        BigDecimal availableHoursWeekly = inputHandler.getBigDecimalInput("Available Hours Weekly");
        BigDecimal totalVolunteeredHours = inputHandler.getBigDecimalInput("Total Volunteered Hours");

        // Create a new volunteer object
        Volunteer newVolunteer = new Volunteer(name, email, password, skills, availableHoursWeekly, totalVolunteeredHours, location);

        // Add the volunteer to the application
        app.addVolunteer(newVolunteer);

        System.out.println("Volunteer added successfully!");
    }

    private void addOrganization() {
        System.out.println("\nEnter details for the new organization:");
        String name = inputHandler.getStringInput("Name");
        String email = inputHandler.getStringInput("Email");
        String password = inputHandler.getStringInput("Password");
        String mission = inputHandler.getStringInput("mission");
        String location = inputHandler.getStringInput("location");

        List<Volunteer> volunteers = app.getVolunteers();
        List<VolunteerOpportunities> volunteerOpportunities = app.getVolunteerOpportunities();

        // Create a new organization object
        Organization newOrganization = new Organization(name, email, password, mission, location, volunteerOpportunities, volunteers);

        // Add the organization to the application
        app.addOrganization(newOrganization);

        System.out.println("Organization added successfully!");
    }

    private void addVolunteerOppurtunity() {
        System.out.println("\nEnter details for the new volunteer opportunity:");
        String name = inputHandler.getStringInput("Name");
        String location = inputHandler.getStringInput("Location");
        Organization organization = new Organization(
                inputHandler.getStringInput("Organization Name"),
                inputHandler.getStringInput("Organization Email"),
                inputHandler.getStringInput("Organization Password"),
                inputHandler.getStringInput("Organization Mission"),
                location,
                null, // Assuming you don't assign volunteer opportunities when creating an organization
                null  // Assuming you don't assign volunteers when creating an organization
        );
        // Get skills, interests, and preferred types of work
        List<String> requiredSkills = inputHandler.getListInput("Required skills");

        // Get available hours weekly and total volunteered hours
        BigDecimal requiredWeeklyHours = inputHandler.getBigDecimalInput("required Weekly Hours");
        int requiredNumberOfVolunteers = inputHandler.getIntInput("required Number Of Volunteers");

        // Create a new volunteer object
        VolunteerOpportunities newVolunteerOpportunity = new VolunteerOpportunities(name, location, organization, requiredSkills, requiredWeeklyHours, requiredNumberOfVolunteers);

        // Add the volunteer to the application
        app.addVolunteerOpportunities(newVolunteerOpportunity);

        System.out.println("Volunteer added successfully!");
    }

    private void matchVolunteersToOrganizations() {
        List<Volunteer> volunteers = app.getVolunteers();
        List<VolunteerOpportunities> opportunities = new ArrayList<>();

        // Extract all opportunities from organizations
        List<Organization> organizations = app.getOrganizations();
        for (Organization organization : organizations) {
            opportunities.addAll(organization.getVolunteerOpportunities());
        }

        // Iterate over each volunteer and find matching opportunities
        for (Volunteer volunteer : volunteers) {
            System.out.println("\nMatching opportunities for " + volunteer.getName() + ":");
            for (VolunteerOpportunities opportunity : opportunities) {
                int score = matchingEngine.calculateMatchingScore(volunteer, opportunity);
                System.out.println("- " + opportunity.getOppurtunityName() + ": Score " + score);
            }
        }

        matchingEngine.printMatches(volunteers, opportunities);
    }

}
