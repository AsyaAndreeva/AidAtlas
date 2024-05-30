package AidAtlas.data;

import java.util.ArrayList;
import java.util.List;

public class Organization extends User {
    private String mission;
    private String location;
    private List<VolunteerOpportunities> volunteerOpportunities;
    private List<Volunteer> volunteers;

    public Organization(String name, String email, String password, String mission, String location,
                        List<VolunteerOpportunities> volunteerOpportunities, List<Volunteer> volunteers) {
        super(name, email, password, UserRole.ORGANIZATION);
        this.mission = mission;
        this.location = location;
        this.volunteerOpportunities = (volunteerOpportunities != null) ? volunteerOpportunities : new ArrayList<>();
        this.volunteers = (volunteers != null) ? volunteers : new ArrayList<>();
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<VolunteerOpportunities> getVolunteerOpportunities() {
        return volunteerOpportunities;
    }

    public void setVolunteerOpportunities(List<VolunteerOpportunities> volunteerOpportunities) {
        this.volunteerOpportunities = volunteerOpportunities;
    }

    public List<Volunteer> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(List<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }
}
