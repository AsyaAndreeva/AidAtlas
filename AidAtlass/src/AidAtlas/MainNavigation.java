package AidAtlas;

import java.math.BigDecimal;
import java.util.*;

import static AidAtlas.MatchingEngine.getMatchingParametersForOrganization;

public class MainNavigation {
    private static ProfileManagement profileManagement;
    private Scanner scanner;


    public MainNavigation(ProfileManagement profileManagement, Scanner scanner) {
        this.profileManagement = profileManagement;
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
            displayMainMenu();
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    MainNavigation.registerUser(scanner, profileManagement);
                    break;
                case 2:
                    MainNavigation.loginUser(scanner, profileManagement);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option! Please choose again.");
            }
        }
    }

    static void registerUser(Scanner scanner, ProfileManagement profileManagement) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.print("Are you registering as a volunteer or organization? (VOLUNTEER/ORGANIZATION): ");
        String roleInput = scanner.nextLine().toUpperCase();

        UserRole role = UserRole.valueOf(roleInput);
        if (role == UserRole.VOLUNTEER || role == UserRole.ORGANIZATION) {
            // Create a new user profile
            User newUser = profileManagement.createProfile(name, email, password, role);
            if (newUser != null) {
                System.out.println("Registration successful!");
                if (role == UserRole.VOLUNTEER) {
                    // If registered as volunteer, show volunteer menu
                    showVolunteerMenu(scanner, (Volunteer) newUser, profileManagement);
                } else {
                    // If registered as organization, show organization menu
                    showOrganizationMenu(scanner, (Organization) newUser, profileManagement);
                }
            } else {
                System.out.println("Registration failed. Please try again.");
            }
        } else {
            System.out.println("Invalid role! Please choose VOLUNTEER or ORGANIZATION.");
        }
    }

    static void loginUser(Scanner scanner, ProfileManagement profileManagement) {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = profileManagement.login(email, password);
        if (user != null) {
            System.out.println("Login successful!");
            if (user.getRole() == UserRole.VOLUNTEER) {
                // If logged in as volunteer, show volunteer menu
                showVolunteerMenu(scanner, (Volunteer) user, profileManagement);
            } else {
                // If logged in as organization, show organization menu
                showOrganizationMenu(scanner, (Organization) user, profileManagement);
            }
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    private static void showVolunteerMenu(Scanner scanner, Volunteer volunteer, ProfileManagement profileManagement) {
        while (true) {
            System.out.println("\nVolunteer Menu");
            System.out.println("1. View Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. View Matching Opportunities");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    volunteer.ViewProfile();
                    break;
                case 2:
                    volunteer.editProfile();
                    break;
                case 3:
                    viewOpportunitiesForLoggedVolunteer(volunteer, profileManagement);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option! Please choose again.");
            }
        }
    }

    private static void showOrganizationMenu(Scanner scanner, Organization organization, ProfileManagement profileManagement) {
        while (true) {
            System.out.println("\nOrganization Menu");
            System.out.println("1. View Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. Create Opportunity");
            System.out.println("4. View Matched Volunteers");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    organization.ViewProfile();
                    break;
                case 2:
                    organization.editProfile();
                    break;
                case 3:
                    createOpportunity(scanner, organization, profileManagement);
                    break;
                case 4:
                    viewVolunteersForLoggedOrganization(organization, profileManagement);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option! Please choose again.");
            }
        }
    }

    private static void createOpportunity(Scanner scanner, Organization organization, ProfileManagement profileManagement) {
        System.out.println("Creating opportunity for " + organization.getName() + "...");

        // Gather information for the new opportunity
        System.out.print("Enter opportunity name: ");
        String opportunityName = scanner.nextLine();
        System.out.print("Enter location: ");
        String location = scanner.nextLine();
        System.out.print("Enter required weekly hours: ");
        BigDecimal requiredWeeklyHours = scanner.nextBigDecimal();
        System.out.print("Enter required number of volunteers: ");
        int requiredNumberOfVolunteers = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        // Choose skills required for the opportunity
        List<String> requiredSkills = chooseSkills();

        // Create the opportunity
        VolunteerOpportunities newOpportunity = new VolunteerOpportunities(opportunityName, location, organization, requiredSkills, requiredWeeklyHours, requiredNumberOfVolunteers);

        // Now you can use matchedVolunteers for further processing if needed
        // Add the opportunity to the organization's list
        organization.getVolunteerOpportunities().add(newOpportunity);

        System.out.println("Opportunity created successfully.");
    }

    private static List<String> chooseSkills() {
        Scanner scanner = new Scanner(System.in);
        List<String> selectedSkills = new ArrayList<>();

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

        return selectedSkills;
    }


    private static void viewOpportunitiesForLoggedVolunteer(Volunteer volunteer, ProfileManagement profileManagement) {
        List<Organization> organizations = profileManagement.getAllOrganizations();
        List<VolunteerOpportunities> availableOpportunities = new ArrayList<>();
        for (Organization organization : organizations) {
            availableOpportunities.addAll(organization.getVolunteerOpportunities());
        }

        if (!availableOpportunities.isEmpty()) {
            MatchingEngine matchingEngine = new MatchingEngine(); // Create an instance of MatchingEngine
            Map<VolunteerOpportunities, List<MatchedVolunteer>> matches = matchingEngine.findMatchesForOpportunities(Collections.singletonList(volunteer), availableOpportunities);

            // Sort matched opportunities by score in descending order
            List<Map.Entry<VolunteerOpportunities, List<MatchedVolunteer>>> sortedMatches = new ArrayList<>(matches.entrySet());
            sortedMatches.sort((entry1, entry2) -> {
                int score1 = entry1.getValue().isEmpty() ? 0 : entry1.getValue().get(0).getScore();
                int score2 = entry2.getValue().isEmpty() ? 0 : entry2.getValue().get(0).getScore();
                return Integer.compare(score2, score1); // Descending order
            });

            // Print matched opportunities
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
                System.out.println("From Organization: " + organization.getName());
                System.out.println("Score: " + (matchedVolunteers.isEmpty() ? "N/A" : matchedVolunteers.get(0).getScore()));
                System.out.println("Matching Parameters: " + MatchingEngine.getMatchingParameters(volunteer, opportunity));
            }
        } else {
            System.out.println("No opportunities available.");
        }
    }

    private static void viewVolunteersForLoggedOrganization(Organization organization, ProfileManagement profileManagement) {
        List<Volunteer> volunteers = profileManagement.getAllVolunteers(); // Get all volunteers
        List<VolunteerOpportunities> availableOpportunities = organization.getVolunteerOpportunities();

        if (!availableOpportunities.isEmpty()) {
            MatchingEngine matchingEngine = new MatchingEngine();

            for (VolunteerOpportunities opportunity : availableOpportunities) {
                System.out.println("\nOpportunity: " + opportunity.getOppurtunityName());
                System.out.println("Matching Volunteers:");

                // Find matches for the current opportunity
                Map<VolunteerOpportunities, List<MatchedVolunteer>> matchedVolunteersMap = matchingEngine.findMatchesForOpportunities(volunteers, Collections.singletonList(opportunity));
                List<MatchedVolunteer> matchedVolunteers = matchedVolunteersMap.getOrDefault(opportunity, new ArrayList<>());

                // Sort matched volunteers by score in descending order
                matchedVolunteers.sort(Comparator.comparingInt(MatchedVolunteer::getScore).reversed());

                // Print matched volunteers
                for (Volunteer volunteer : volunteers) {
                    boolean found = false;
                    for (MatchedVolunteer matchedVolunteer : matchedVolunteers) {
                        if (matchedVolunteer.getVolunteer().equals(volunteer)) {
                            System.out.println("- Volunteer: " + volunteer.getName());
                            System.out.println("  Score: " + matchedVolunteer.getScore());
                            System.out.println("  Matching Parameters:");
                            System.out.println("    Required Hours: " + opportunity.getRequiredWeeklyHours());
                            System.out.println("    Location: " + organization.getLocation());
                            System.out.println("    Matched Skills:");
                            List<String> volunteerSkills = volunteer.getSkills();
                            List<String> opportunitySkills = opportunity.getRequiredSkills();
                            for (String skill : volunteerSkills) {
                                if (opportunitySkills.contains(skill)) {
                                    System.out.println("      " + skill);
                                }
                            }
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("- Volunteer: " + volunteer.getName());
                        System.out.println("  Score: 0"); // No match, score is 0
                        System.out.println("  Matching Parameters:");
                        System.out.println("    Required Hours: " + opportunity.getRequiredWeeklyHours());
                        System.out.println("    Location: " + organization.getLocation());
                        System.out.println("    Matched Skills: None");
                    }
                }

                // Print required number of volunteers
                int requiredVolunteers = VolunteerOpportunities.getRequiredNumberOfVolunteers();
                System.out.println("\nRequired number of volunteers: " + requiredVolunteers);
            }
        } else {
            System.out.println("No opportunities available.");
        }
    }








}
