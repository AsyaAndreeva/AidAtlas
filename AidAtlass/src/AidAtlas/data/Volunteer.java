package AidAtlas.data;

import AidAtlas.services.skillsManagment.ChooseSkills;
import AidAtlas.services.skillsManagment.ConsoleSkillChooser;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Volunteer extends User  {
    private final Skills skills;
    private BigDecimal availableHoursWeekly;
    private BigDecimal totalVolunteeredHours;
    private String location;

    public Volunteer(String name, String email, String password, Set<String> skillsSet, BigDecimal availableHoursWeekly, BigDecimal totalVolunteeredHours, String location) {
        super(name, email, password, UserRole.VOLUNTEER);
        this.skills = new Skills(skillsSet);
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

    public Set<String> getSkills() {
        return skills.getSkillsSet();
    }

    public void setSkills(Set<String> skillsSet) {
        this.skills.setSkillsSet(skillsSet);
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

    public void editSkills() {
        ChooseSkills skillChooser = new ConsoleSkillChooser();
        List<String> newSkills = skillChooser.chooseSkills();
        setSkills(new HashSet<>(newSkills));
    }

    public void editLocation(Scanner scanner) {
        System.out.println("Enter new location: ");
        String newLocation = scanner.nextLine();
        setLocation(newLocation);
    }

    public void editAvailableHoursWeekly(Scanner scanner) {
        System.out.println("Enter new available hours weekly: ");
        BigDecimal newAvailableHours = new BigDecimal(scanner.nextLine());
        setAvailableHoursWeekly(newAvailableHours);
    }

    public void editTotalVolunteeredHours(Scanner scanner) {
        System.out.println("Enter new total volunteered hours: ");
        BigDecimal newTotalVolunteeredHours = new BigDecimal(scanner.nextLine());
        setTotalVolunteeredHours(newTotalVolunteeredHours);
    }

}
