package AidAtlas.services.profileManagment;

import AidAtlas.data.User;
import AidAtlas.data.UserRole;
import AidAtlas.data.Volunteer;
import AidAtlas.data.Organization;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProfileManagement {
    private final AuthenticationService authService;
    private final List<Organization> organizations;
    private final List<Volunteer> volunteers;
    private final Set<String> skillsSet;

    public ProfileManagement(AuthenticationService authService, UserRegistrationService userRegistrationService) {
        this.authService = authService;
        this.organizations = new ArrayList<>();
        this.volunteers = new ArrayList<>();
        this.skillsSet = new HashSet<>();
    }

    public User login(String email, String password) {
        return authService.login(email, password);
    }

    public User createProfile(String name, String email, String password, UserRole role) {
        User newUser = null;
        if (role == UserRole.VOLUNTEER) {
            newUser = new Volunteer(name, email, password, skillsSet, BigDecimal.ZERO, BigDecimal.ZERO, "");
            volunteers.add((Volunteer) newUser);
        } else if (role == UserRole.ORGANIZATION) {
            newUser = new Organization(name, email, password, "", "", new ArrayList<>(), new ArrayList<>());
            organizations.add((Organization) newUser);
        } else {
            System.out.println("Invalid role!");
        }
        if (newUser != null) {
            AuthenticationService.registerUser(newUser); // Correctly call the instance method
        }
        return newUser;
    }

    // Method to get all organizations
    public List<Organization> getAllOrganizations() {
        return organizations;
    }

    // Method to get all volunteers
    public List<Volunteer> getAllVolunteers() {
        return volunteers;
    }
}
