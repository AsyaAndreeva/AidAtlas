package AidAtlas.services.volunteerViewerManagment;

import AidAtlas.data.MatchedVolunteer;
import AidAtlas.data.Organization;
import AidAtlas.data.Volunteer;
import AidAtlas.data.VolunteerOpportunities;
import AidAtlas.services.matching.MatchingEngine;
import AidAtlas.services.opportunityManagment.OpportunityFetcher;
import AidAtlas.services.volunteerViewerManagment.VolunteerFetcher;
import AidAtlas.services.volunteerViewerManagment.VolunteerSorter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class VolunteerViewer {
    private final MatchingEngine matchingEngine;
    private final VolunteerFetcher volunteerFetcher;
    private final OpportunityFetcher opportunityFetcher;
    private final VolunteerSorter volunteerSorter;

    public VolunteerViewer(MatchingEngine matchingEngine, VolunteerFetcher volunteerFetcher, OpportunityFetcher opportunityFetcher, VolunteerSorter volunteerSorter) {
        this.matchingEngine = matchingEngine;
        this.volunteerFetcher = volunteerFetcher;
        this.opportunityFetcher = opportunityFetcher;
        this.volunteerSorter = volunteerSorter;
    }

    public void viewVolunteersForLoggedOrganization(Organization organization) {
        try {
            List<Volunteer> volunteers = volunteerFetcher.fetchAllVolunteers();
            List<VolunteerOpportunities> availableOpportunities = opportunityFetcher.fetchOpportunitiesForOrganization(organization);

            if (!availableOpportunities.isEmpty()) {
                for (VolunteerOpportunities opportunity : availableOpportunities) {
                    displayOpportunity(opportunity, volunteers);
                }
            } else {
                displayNoOpportunities();
            }
        } catch (Exception e) {
            handleError(e);
        }
    }

    private void displayOpportunity(VolunteerOpportunities opportunity, List<Volunteer> volunteers) {
        System.out.println("\nOpportunity: " + opportunity.getOpportunityName());
        System.out.println("Matching Volunteers:");

        Map<VolunteerOpportunities, List<MatchedVolunteer>> matchedVolunteersMap =
                matchingEngine.findMatchesForOpportunities(volunteers, Collections.singletonList(opportunity));
        List<MatchedVolunteer> matchedVolunteers = matchedVolunteersMap.getOrDefault(opportunity, Collections.emptyList());

        List<MatchedVolunteer> sortedVolunteers = volunteerSorter.sortVolunteersByScore(matchedVolunteers);
        displayVolunteers(sortedVolunteers, opportunity);
    }

    private void displayNoOpportunities() {
        System.out.println("No opportunities available.");
    }

    private void handleError(Exception e) {
        System.out.println("An error occurred while viewing volunteers: " + e.getMessage());
    }

    private void displayVolunteers(List<MatchedVolunteer> matchedVolunteers, VolunteerOpportunities opportunity) {
        for (MatchedVolunteer matchedVolunteer : matchedVolunteers) {
            Volunteer volunteer = matchedVolunteer.getVolunteer();
            System.out.println("- Volunteer: " + volunteer.getName());
            System.out.println("  Score: " + matchedVolunteer.getScore());
            System.out.println("  Matching Parameters:");
            System.out.println(MatchingEngine.getMatchingParameters(volunteer, opportunity));
        }

        displayRequiredVolunteers();
    }

    private void displayRequiredVolunteers() {
        System.out.println("\nRequired number of volunteers: " + VolunteerOpportunities.getRequiredNumberOfVolunteers());
    }
}
