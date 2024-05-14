package AidAtlas;

import java.math.BigDecimal;
import java.util.List;

public class VolunteerOpportunities implements ChooseSkills {
    private String oppurtunityName;

    private String location;
    private Organization organization;
    private List<String> requiredSkills;
    private BigDecimal requiredWeeklyHours;
    private static int requiredNumberOfVolunteers;

    public VolunteerOpportunities(String oppurtunityName, String location, Organization organization, List<String> requiredSkills, BigDecimal requiredWeeklyHours, int requiredNumberOfVolunteers) {
        this.oppurtunityName = oppurtunityName;
        this.location = location;
        this.organization = organization;
        this.requiredSkills = requiredSkills;
        this.requiredWeeklyHours = requiredWeeklyHours;
        VolunteerOpportunities.requiredNumberOfVolunteers = requiredNumberOfVolunteers;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOppurtunityName() {
        return oppurtunityName;
    }

    public void setOppurtunityName(String oppurtunityName) {
        this.oppurtunityName = oppurtunityName;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<String> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<String> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public BigDecimal getRequiredWeeklyHours() {
        return requiredWeeklyHours;
    }

    public void setRequiredWeeklyHours(BigDecimal requiredWeeklyHours) {
        this.requiredWeeklyHours = requiredWeeklyHours;
    }

    public static int getRequiredNumberOfVolunteers() {
        return requiredNumberOfVolunteers;
    }

    public void setRequiredNumberOfVolunteers(int requiredNumberOfVolunteers) {
        VolunteerOpportunities.requiredNumberOfVolunteers = requiredNumberOfVolunteers;
    }
}
