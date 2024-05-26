package AidAtlas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProfileManagement {
    private final AuthenticationService authService;

    private final List<Organization> organizations;

    private final List<Volunteer> volunteers;

    public ProfileManagement(AuthenticationService authService) {
        this.authService = authService;
        this.organizations = new ArrayList<>();
        this.volunteers = new ArrayList<>();
    }

    public User login(String email, String password) {
        return authService.login(email, password);
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

    // Method to get all organizations
    public List<Organization> getAllOrganizations() {
        return organizations;
    }

    public List<Volunteer> getAllVolunteers() {
        return volunteers;
    }
}
