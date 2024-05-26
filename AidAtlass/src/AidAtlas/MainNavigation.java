package AidAtlas;

import java.math.BigDecimal;
import java.util.*;

public class MainNavigation {
    private static ProfileManagement profileManagement;
    private final Scanner scanner;

    public MainNavigation(ProfileManagement profileManagement, Scanner scanner) {
        MainNavigation.profileManagement = profileManagement;
        this.scanner = scanner;
    }

    public void displayMainMenu() {
        System.out.println("\nWelcome to AidAtlas!");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
    }

    public void handleMainMenu() {
        while (true) {
            try {
                displayMainMenu();
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine().trim()); // Use nextLine() to avoid input issues

                switch (choice) {
                    case 1:
                        clearConsole();
                        ProfileRegistration.registerUser(scanner, profileManagement);
                        break;
                    case 2:
                        clearConsole();
                        loginUser(scanner, profileManagement);
                        break;
                    case 3:
                        clearConsole();
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option! Please choose again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    static void clearConsole() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }

    static void loginUser(Scanner scanner, ProfileManagement profileManagement) {
        try {
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            User user = profileManagement.login(email, password);
            if (user != null) {
                System.out.println("Login successful!");
                ProfileRegistration.navigateToRoleSpecificMenu(scanner, profileManagement, user, user.getRole());
            } else {
                System.out.println("Invalid email or password.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred during login: " + e.getMessage());
        }
    }

    static void createOpportunity(Scanner scanner, Organization organization, ProfileManagement profileManagement) {
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
            List<String> requiredSkills = chooseSkills(scanner);

            // Create the opportunity
            VolunteerOpportunities newOpportunity = new VolunteerOpportunities(opportunityName, opportunityLocation, organization, requiredSkills, requiredWeeklyHours, requiredNumberOfVolunteers);

            // Add the opportunity to the organization's list
            organization.getVolunteerOpportunities().add(newOpportunity);

            System.out.println("Opportunity created successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format! Please enter valid numerical values.");
        } catch (Exception e) {
            System.out.println("An error occurred while creating the opportunity: " + e.getMessage());
        }
    }

    private static List<String> chooseSkills(Scanner scanner) {
        List<String> selectedSkills = new ArrayList<>();

        try {
            System.out.println("Choose skills from the following list:");
            int index = 1;
            for (String skill : Volunteer.PredefinedSkills) {
                System.out.println(index + ". " + skill);
                index++;
            }

            System.out.println("Enter the numbers corresponding to the skills you want (comma-separated): ");
            String[] selectedSkillsIndices = scanner.nextLine().split("\\s*,\\s*");

            for (String indexStr : selectedSkillsIndices) {
                int selectedIndex = Integer.parseInt(indexStr);
                if (selectedIndex >= 1 && selectedIndex <= Volunteer.PredefinedSkills.size()) {
                    selectedSkills.add((String) Volunteer.PredefinedSkills.toArray()[selectedIndex - 1]);
                } else {
                    System.out.println("Invalid skill index: " + selectedIndex);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter valid skill numbers.");
        } catch (Exception e) {
            System.out.println("An error occurred while choosing skills: " + e.getMessage());
        }

        return selectedSkills;
    }

    static void viewOpportunitiesForLoggedVolunteer(Volunteer volunteer, ProfileManagement profileManagement) {
        try {
            List<Organization> organizations = profileManagement.getAllOrganizations();
            List<VolunteerOpportunities> availableOpportunities = new ArrayList<>();
            for (Organization organization : organizations) {
                availableOpportunities.addAll(organization.getVolunteerOpportunities());
            }

            if (!availableOpportunities.isEmpty()) {
                MatchingEngine matchingEngine = new MatchingEngine(); // Create an instance of MatchingEngine
                Map<VolunteerOpportunities, List<MatchedVolunteer>> matches = matchingEngine.findMatchesForOpportunities(Collections.singletonList(volunteer), availableOpportunities);

                List<Map.Entry<VolunteerOpportunities, List<MatchedVolunteer>>> sortedMatches = new ArrayList<>(matches.entrySet());
                sortedMatches.sort((entry1, entry2) -> {
                    int score1 = entry1.getValue().isEmpty() ? 0 : entry1.getValue().get(0).getScore();
                    int score2 = entry2.getValue().isEmpty() ? 0 : entry2.getValue().get(0).getScore();
                    return Integer.compare(score2, score1); // Descending order
                });

                for (Map.Entry<VolunteerOpportunities, List<MatchedVolunteer>> entry : sortedMatches) {
                    VolunteerOpportunities opportunity = entry.getKey();
                    List<MatchedVolunteer> matchedVolunteers = entry.getValue();
                    Organization organization = null;
                    for (Organization org : organizations) {
                        if (org.getVolunteerOpportunities().contains(opportunity)) {
                            organization = org;
                            break;
                        }
                    }
                    System.out.println("\nOpportunity: " + opportunity.getOppurtunityName());
                    assert organization != null;
                    System.out.println("From Organization: " + organization.getName());
                    System.out.println("Score: " + (matchedVolunteers.isEmpty() ? "N/A" : matchedVolunteers.get(0).getScore()));
                    System.out.println("Matching Parameters: \n" + MatchingEngine.getMatchingParameters(volunteer, opportunity));
                }
            } else {
                System.out.println("No opportunities available.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while viewing opportunities: " + e.getMessage());
        }
    }

    static void viewVolunteersForLoggedOrganization(Organization organization, ProfileManagement profileManagement) {
        try {
            List<Volunteer> volunteers = profileManagement.getAllVolunteers(); // Get all volunteers
            List<VolunteerOpportunities> availableOpportunities = organization.getVolunteerOpportunities();

            if (!availableOpportunities.isEmpty()) {
                MatchingEngine matchingEngine = new MatchingEngine();

                for (VolunteerOpportunities opportunity : availableOpportunities) {
                    System.out.println("\nOpportunity: " + opportunity.getOppurtunityName());
                    System.out.println("Matching Volunteers:");

                    Map<VolunteerOpportunities, List<MatchedVolunteer>> matchedVolunteersMap = matchingEngine.findMatchesForOpportunities(volunteers, Collections.singletonList(opportunity));
                    List<MatchedVolunteer> matchedVolunteers = matchedVolunteersMap.getOrDefault(opportunity, new ArrayList<>());

                    matchedVolunteers.sort(Comparator.comparingInt(MatchedVolunteer::getScore).reversed());

                    for (Volunteer volunteer : volunteers) {
                        boolean found = false;
                        for (MatchedVolunteer matchedVolunteer : matchedVolunteers) {
                            if (matchedVolunteer.getVolunteer().equals(volunteer)) {
                                System.out.println("- Volunteer: " + volunteer.getName());
                                System.out.println("  Score: " + matchedVolunteer.getScore());
                                System.out.println("  Matching Parameters:");
                                System.out.println(MatchingEngine.getMatchingParameters(volunteer, opportunity));
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println("- Volunteer: " + volunteer.getName());
                            System.out.println("  Score: 0");
                            System.out.println("  Matching Parameters:");
                            System.out.println("    Required Hours: " + opportunity.getRequiredWeeklyHours());
                            System.out.println("    Available Hours: " + volunteer.getAvailableHoursWeekly());
                            System.out.println("    Volunteer Location: " + volunteer.getLocation());
                            System.out.println("    Opportunity Location: " + opportunity.getLocation());
                            System.out.println("    Matched Skills: None");
                        }
                    }

                    int requiredVolunteers = VolunteerOpportunities.getRequiredNumberOfVolunteers();
                    System.out.println("\nRequired number of volunteers: " + requiredVolunteers);
                }
            } else {
                System.out.println("No opportunities available.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while viewing volunteers: " + e.getMessage());
        }
    }
}
