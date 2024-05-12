package AidAtlas;

import java.math.BigDecimal;
import java.util.*;

public class Volunteer extends User implements CreateProfile, EditProfile, ViewProfile {
    static final Set<String> PredefinedSkills = new HashSet<>(Arrays.asList(
            "Programming", "Teaching", "Writing", "Design", "Marketing", "Research", "Cooking", "Driving"));

    private List<String> skills;
    private BigDecimal availableHoursWeekly;
    private BigDecimal totalVolunteeredHours;
    private String location;

    public Volunteer(String name, String email, String password, List<String> skills, BigDecimal availableHoursWeekly, BigDecimal totalVolunteeredHours, String location) {
        super(name, email, password);
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
        System.out.println("2. Available Hours Weekly");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character after reading integer

        switch (choice) {
            case 1:
                editSkills();
                break;
            case 2:
                System.out.println("Enter new available hours weekly: ");
                BigDecimal newAvailableHours = new BigDecimal(scanner.nextLine());
                setAvailableHoursWeekly(newAvailableHours);
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
        System.out.println("Profile updated successfully.");
    }

    private void editSkills() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose from the following skills:");
        int index = 1;
        for (String skill : PredefinedSkills) {
            System.out.println(index + ". " + skill);
            index++;
        }
        System.out.println("Enter the numbers corresponding to the skills you want (comma-separated): ");
        String[] selectedSkillsIndices = scanner.nextLine().split("\\s*,\\s*");
        List<String> newSkills = new ArrayList<>();
        for (String indexStr : selectedSkillsIndices) {
            int selectedIndex = Integer.parseInt(indexStr);
            if (selectedIndex >= 1 && selectedIndex <= PredefinedSkills.size()) {
                newSkills.add((String) PredefinedSkills.toArray()[selectedIndex - 1]);
            } else {
                System.out.println("Invalid skill index: " + selectedIndex);
            }
        }
        setSkills(newSkills);
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
