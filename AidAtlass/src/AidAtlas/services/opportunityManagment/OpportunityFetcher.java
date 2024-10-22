package AidAtlas.services.opportunityManagment;

import AidAtlas.data.Organization;
import AidAtlas.data.VolunteerOpportunities;
import AidAtlas.services.profileManagment.ProfileManagement;

import java.util.ArrayList;
import java.util.List;

public class OpportunityFetcher {
    private final ProfileManagement profileManagement;

    public OpportunityFetcher(ProfileManagement profileManagement) {
        this.profileManagement = profileManagement;
    }

    public List<VolunteerOpportunities> fetchAllOpportunities() {
        List<Organization> organizations = profileManagement.getAllOrganizations();
        List<VolunteerOpportunities> availableOpportunities = new ArrayList<>();
        for (Organization organization : organizations) {
            availableOpportunities.addAll(organization.getVolunteerOpportunities());
        }
        return availableOpportunities;
    }

    public List<VolunteerOpportunities> fetchOpportunitiesForOrganization(Organization organization) {
        return organization.getVolunteerOpportunities();
    }
}
