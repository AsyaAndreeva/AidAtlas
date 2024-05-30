package AidAtlas.services.volunteerManagment;

import AidAtlas.data.Volunteer;

import java.util.Scanner;

public class VolunteerProfileService implements createProfileVolunteer, EditProfileVolunteer, ViewProfileVolunteer {
    private final Scanner scanner;

    public VolunteerProfileService(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void createProfileVolunteer(Volunteer volunteer) {
        System.out.println("Creating volunteer profile...");
        viewProfileDetails(volunteer);
    }

    public void viewProfileVolunteer(Volunteer volunteer) {
        System.out.println("Viewing volunteer profile...");
        viewProfileDetails(volunteer);
    }

    public void editProfileVolunteer(Volunteer volunteer) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like to edit?");
        System.out.println("1. Skills");
        System.out.println("2. Location");
        System.out.println("3. Available Hours Weekly");
        System.out.println("4. Total volunteered hours");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character after reading integer

        switch (choice) {
            case 1:
                volunteer.editSkills();
                break;
            case 2:
                volunteer.editLocation(scanner);
                break;
            case 3:
                volunteer.editAvailableHoursWeekly(scanner);
                break;
            case 4:
                volunteer.editTotalVolunteeredHours(scanner);
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
        System.out.println("Profile updated successfully.");
    }

    private static void viewProfileDetails(Volunteer volunteer) {
        System.out.println("Name: " + volunteer.getName());
        System.out.println("Email: " + volunteer.getEmail());
        System.out.println("Skills: " + volunteer.getSkills());
        System.out.println("Available Hours Weekly: " + volunteer.getAvailableHoursWeekly());
        System.out.println("Total Volunteered Hours: " + volunteer.getTotalVolunteeredHours());
        System.out.println("Location: " + volunteer.getLocation());
    }
}
