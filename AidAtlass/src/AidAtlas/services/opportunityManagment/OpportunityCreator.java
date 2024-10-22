package AidAtlas.services.opportunityManagment;

import AidAtlas.data.Organization;
import AidAtlas.data.VolunteerOpportunities;
import AidAtlas.services.profileManagment.ProfileManagement;
import AidAtlas.services.skillsManagment.ConsoleSkillChooser;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class OpportunityCreator {
    private static ConsoleSkillChooser skillChooser;

    public OpportunityCreator(ConsoleSkillChooser skillChooser) {
        OpportunityCreator.skillChooser = skillChooser;
    }

    public static void createOpportunity(Scanner scanner, Organization organization, ProfileManagement profileManagement) {
        try {
            System.out.println("Creating opportunity for " + organization.getName() + "...");

            // Gather information for the new opportunity
            System.out.print("Enter opportunity name: ");
            String opportunityName = scanner.nextLine();
            System.out.print("Enter location: ");
            String opportunityLocation = scanner.nextLine();
            System.out.print("Enter required weekly hours: ");
            BigDecimal requiredWeeklyHours = new BigDecimal(scanner.nextLine().trim());
            System.out.print("Enter required number of volunteers: ");
            int requiredNumberOfVolunteers = Integer.parseInt(scanner.nextLine().trim());

            // Choose skills required for the opportunity
            List<String> requiredSkills = skillChooser.chooseSkills();

            // Create the opportunity
            VolunteerOpportunities newOpportunity = new VolunteerOpportunities(
                    opportunityName, opportunityLocation, organization,
                    new HashSet<>(requiredSkills), requiredWeeklyHours, requiredNumberOfVolunteers);

            // Add the opportunity to the organization's list
            organization.getVolunteerOpportunities().add(newOpportunity);

            System.out.println("Opportunity created successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format! Please enter valid numerical values.");
        } catch (Exception e) {
            System.out.println("An error occurred while creating the opportunity: " + e.getMessage());
        }
    }
}
