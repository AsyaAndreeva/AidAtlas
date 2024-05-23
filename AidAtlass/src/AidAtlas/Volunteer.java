package AidAtlas;

import java.math.BigDecimal;
import java.util.*;

public class Volunteer extends User implements CreateProfile, EditProfile, ViewProfile, ChooseSkills {
    private List<String> skills;
    private BigDecimal availableHoursWeekly;
    private BigDecimal totalVolunteeredHours;
    private String location;

    public Volunteer(String name, String email, String password, List<String> skills, BigDecimal availableHoursWeekly, BigDecimal totalVolunteeredHours, String location) {
        super(name, email, password, UserRole.VOLUNTEER);
        this.skills = skills;
        this.availableHoursWeekly = availableHoursWeekly;
        this.totalVolunteeredHours = totalVolunteeredHours;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }


    public BigDecimal getAvailableHoursWeekly() {
        return availableHoursWeekly;
    }

    public void setAvailableHoursWeekly(BigDecimal availableHoursWeekly) {
        this.availableHoursWeekly = availableHoursWeekly;
    }

    public BigDecimal getTotalVolunteeredHours() {
        return totalVolunteeredHours;
    }

    public void setTotalVolunteeredHours(BigDecimal totalVolunteeredHours) {
        this.totalVolunteeredHours = totalVolunteeredHours;
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
        System.out.println("1. Skills");
        System.out.println("2. Location");
        System.out.println("3. Available Hours Weekly");
        System.out.println("4. Total volunteered hours");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character after reading integer

        switch (choice) {
            case 1:
                skills = chooseSkills(); // Utilize chooseSkills method
                break;
            case 2:
                System.out.println("Enter new location: ");
                String newLocation = scanner.nextLine();
                setLocation(newLocation);
                break;
            case 3:
                System.out.println("Enter new available hours weekly: ");
                BigDecimal newAvailableHours = new BigDecimal(scanner.nextLine());
                setAvailableHoursWeekly(newAvailableHours);
                break;
            case 4:
                System.out.println("Enter new total volunteered hours: ");
                BigDecimal newTotalVolunteeredHours = new BigDecimal(scanner.nextLine());
                setTotalVolunteeredHours(newTotalVolunteeredHours);
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
        System.out.println("Profile updated successfully.");
    }

    @Override
    public void ViewProfile() {
        System.out.println("Viewing volunteer profile...");
        ViewProfileDetails();
    }

    private void ViewProfileDetails() {
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Skills: " + getSkills());
        System.out.println("Available Hours Weekly: " + getAvailableHoursWeekly());
        System.out.println("Total Volunteered Hours: " + getTotalVolunteeredHours());
    }
}
