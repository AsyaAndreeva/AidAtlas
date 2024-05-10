package AidAtlas;

import java.util.ArrayList;
import java.util.List;

public class AidAtlasApp {
    private List<Volunteer> volunteers;
    private List<VolunteerOpportunities> volunteerOpportunities;
    private List<Organization> organizations;
    private Navigation navigation;

    public AidAtlasApp() {
        this.volunteers = new ArrayList<>();
        this.volunteerOpportunities = new ArrayList<>();
        this.organizations = new ArrayList<>();
        this.navigation = new Navigation(this);
    }

    public List<Volunteer> getVolunteers() {
        return volunteers;
    }

    // Method to add a volunteer
    public void addVolunteer(Volunteer volunteer) {
        volunteers.add(volunteer);
    }

    public List<VolunteerOpportunities> getVolunteerOpportunities() {
        return volunteerOpportunities;
    }

    // Method to add a volunteer
    public void addVolunteerOpportunities(VolunteerOpportunities volunteerOpportunity) {
        volunteerOpportunities.add(volunteerOpportunity);
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void addOrganization(Organization organization){
        organizations.add(organization);
    }

    public void start() {
        navigation.handleMainMenu();
    }

    public static void main(String[] args) {
        AidAtlasApp app = new AidAtlasApp();
        app.start();
    }
}
