package AidAtlas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProfileManagement {
    private AuthenticationService authService;
    private User currentUser;

    private List<Organization> organizations;

    private List<Volunteer> volunteers;

    public ProfileManagement(AuthenticationService authService) {
        this.authService = authService;
        this.organizations = new ArrayList<>();
        this.volunteers = new ArrayList<>();
    }

    public void register(User user) {
        authService.registerUser(user);
    }

    public User login(String email, String password) {
        currentUser = authService.login(email, password);
        return currentUser;
    }

    public User createProfile(String name, String email, String password, UserRole role) {
        User newUser = null;
        if (role == UserRole.VOLUNTEER) {
            newUser = new Volunteer(name, email, password, new ArrayList<>(), BigDecimal.ZERO, BigDecimal.ZERO, "");
            volunteers.add((Volunteer) newUser);
        } else if (role == UserRole.ORGANIZATION) {
            newUser = new Organization(name, email, password, "", "", new ArrayList<>(), new ArrayList<>());
            organizations.add((Organization) newUser);
        } else {
            System.out.println("Invalid role!");
        }
        if (newUser != null) {
            authService.registerUser(newUser);
        }
        return newUser;
    }

    public void registerOrganization(Organization organization) {
        authService.registerUser(organization);
        organizations.add(organization); // Add the organization to the list
    }

    // Method to get all organizations
    public List<Organization> getAllOrganizations() {
        return organizations;
    }

    public List<Volunteer> getAllVolunteers() {
        return volunteers;
    }

    public void editProfile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like to edit?");
        System.out.println("1. Name");
        System.out.println("2. Email");
        System.out.println("3. Password");
        System.out.println("4. Location (for volunteers)");
        System.out.println("5. Mission (for organizations)");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character after reading integer

        switch (choice) {
            case 1:
                System.out.println("Enter new name: ");
                String newName = scanner.nextLine();
                currentUser.setName(newName);
                break;
            case 2:
                System.out.println("Enter new email: ");
                String newEmail = scanner.nextLine();
                currentUser.setEmail(newEmail);
                break;
            case 3:
                System.out.println("Enter new password: ");
                String newPassword = scanner.nextLine();
                currentUser.setPassword(newPassword);
                break;
            case 4:
                if (currentUser instanceof Volunteer) {
                    System.out.println("Enter new location: ");
                    String newLocation = scanner.nextLine();
                    ((Volunteer) currentUser).setLocation(newLocation);
                } else {
                    System.out.println("Invalid option!");
                }
                break;
            case 5:
                if (currentUser instanceof Organization) {
                    System.out.println("Enter new mission: ");
                    String newMission = scanner.nextLine();
                    ((Organization) currentUser).setMission(newMission);
                } else {
                    System.out.println("Invalid option!");
                }
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
        System.out.println("Profile updated successfully.");
    }

    public void viewProfile() {
        if (currentUser != null) {
            System.out.println("Viewing profile...");
            System.out.println("Name: " + currentUser.getName());
            System.out.println("Email: " + currentUser.getEmail());
            if (currentUser instanceof Volunteer) {
                System.out.println("Location: " + ((Volunteer) currentUser).getLocation());
            } else if (currentUser instanceof Organization) {
                System.out.println("Mission: " + ((Organization) currentUser).getMission());
            }
        } else {
            System.out.println("No user logged in.");
        }
    }

    public void showMatchedOpportunities() {
        if (currentUser != null) {
            if (currentUser instanceof Volunteer) {
                // Implementation to show matched opportunities for volunteers
                System.out.println("Showing matched opportunities for volunteer...");
            } else if (currentUser instanceof Organization) {
                // Implementation to show matched opportunities for organizations
                System.out.println("Showing matched opportunities for organization...");
            }
        } else {
            System.out.println("No user logged in.");
        }
    }

    public void createOpportunity() {
        if (currentUser != null && currentUser instanceof Organization) {
            // Implementation to create opportunity, only available for organizations
            System.out.println("Creating opportunity...");
        } else {
            System.out.println("You need to be logged in as an organization to create opportunities.");
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public UserRole getCurrentUserRole() {
        return currentUser != null ? currentUser.getRole() : null;
    }
}
