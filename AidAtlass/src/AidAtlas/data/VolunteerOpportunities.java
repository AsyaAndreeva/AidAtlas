package AidAtlas.data;

import AidAtlas.services.skillsManagment.ChooseSkills;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class VolunteerOpportunities implements ChooseSkills {
    private String opportunityName;
    private String location;
    private Organization organization;
    private Set<String> requiredSkills;
    private BigDecimal requiredWeeklyHours;
    private static int requiredNumberOfVolunteers;

    public VolunteerOpportunities(String opportunityName, String location, Organization organization, Set<String> requiredSkills, BigDecimal requiredWeeklyHours, int requiredNumberOfVolunteers) {
        this.opportunityName = opportunityName;
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

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Set<String> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(Set<String> requiredSkills) {
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

    @Override
    public List<String> chooseSkills() {
        return null;
    }
}
