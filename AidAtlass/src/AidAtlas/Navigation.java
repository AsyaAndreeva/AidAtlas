package AidAtlas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Navigation {
    private AidAtlasApp app;
    private InputHandler inputHandler;

    public Navigation(AidAtlasApp app) {
        this.app = app;
        this.inputHandler = new InputHandler();
    }

    public void displayMainMenu() {
        System.out.println("\nWelcome to AidAtlas!");
        System.out.println("1. View Volunteers");
        System.out.println("2. View Organizations");
        System.out.println("3. Add Volunteer");
        System.out.println("4. Add Organization");
        System.out.println("5. Exit");
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
                    addVolunteer();
                    break;
                case 4:
                    addOrganization();
                    break;
                case 5:
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
        // Get skills, interests, and preferred types of work
        List<String> skills = inputHandler.getListInput("Skills");
        List<String> interests = inputHandler.getListInput("Interests");
        List<String> preferredTypesOfWork = inputHandler.getListInput("Preferred Types of Work");

        // Get available hours weekly and total volunteered hours
        BigDecimal availableHoursWeekly = inputHandler.getBigDecimalInput("Available Hours Weekly");
        BigDecimal totalVolunteeredHours = inputHandler.getBigDecimalInput("Total Volunteered Hours");

        // Create a new volunteer object
        Volunteer newVolunteer = new Volunteer(name, email, password, skills, interests, preferredTypesOfWork, availableHoursWeekly, totalVolunteeredHours);

        // Add the volunteer to the application
        app.addVolunteer(newVolunteer);

        System.out.println("Volunteer added successfully!");
    }

    private void addOrganization() {
        System.out.println("\nEnter details for the new organization:");
        String name = inputHandler.getStringInput("Name");
        String email = inputHandler.getStringInput("Email");
        String password = inputHandler.getStringInput("Password");
        String organizationName = inputHandler.getStringInput("organizationName");
        String mission = inputHandler.getStringInput("mission");
        String location = inputHandler.getStringInput("location");

        List<Volunteer> volunteers = app.getVolunteers();
        List<String> volunteerOpportunities = inputHandler.getListInput("volunteerOpportunities");

        // Create a new organization object
        Organization newOrganization = new Organization(name, email, password, organizationName, mission, location, volunteerOpportunities, volunteers);

        // Add the organization to the application
        app.addOrganization(newOrganization);

        System.out.println("Organization added successfully!");
    }
}
