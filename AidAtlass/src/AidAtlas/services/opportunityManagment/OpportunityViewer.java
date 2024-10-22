package AidAtlas.services.opportunityManagment;

import AidAtlas.data.MatchedVolunteer;
import AidAtlas.data.Organization;
import AidAtlas.data.Volunteer;
import AidAtlas.data.VolunteerOpportunities;
import AidAtlas.services.matching.MatchingEngine;
import AidAtlas.services.opportunityManagment.OpportunityFetcher;
import AidAtlas.services.opportunityManagment.OpportunitySorter;

import java.util.List;
import java.util.Map;

public class OpportunityViewer {
    private final MatchingEngine matchingEngine;
    private final OpportunityFetcher opportunityFetcher;
    private final OpportunitySorter opportunitySorter;

    public OpportunityViewer(MatchingEngine matchingEngine, OpportunityFetcher opportunityFetcher, OpportunitySorter opportunitySorter) {
        this.matchingEngine = matchingEngine;
        this.opportunityFetcher = opportunityFetcher;
        this.opportunitySorter = opportunitySorter;
    }

    public void viewOpportunitiesForLoggedVolunteer(Volunteer volunteer) {
        try {
            List<VolunteerOpportunities> availableOpportunities = opportunityFetcher.fetchAllOpportunities();

            if (!availableOpportunities.isEmpty()) {
                Map<VolunteerOpportunities, List<MatchedVolunteer>> matches =
                        matchingEngine.findMatchesForOpportunities(List.of(volunteer), availableOpportunities);

                List<Map.Entry<VolunteerOpportunities, List<MatchedVolunteer>>> sortedMatches = opportunitySorter.sortMatchesByScore(matches);

                displaySortedMatches(sortedMatches, volunteer);
            } else {
                System.out.println("No opportunities available.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while viewing opportunities: " + e.getMessage());
        }
    }

    private void displaySortedMatches(List<Map.Entry<VolunteerOpportunities, List<MatchedVolunteer>>> sortedMatches, Volunteer volunteer) {
        for (Map.Entry<VolunteerOpportunities, List<MatchedVolunteer>> entry : sortedMatches) {
            VolunteerOpportunities opportunity = entry.getKey();
            List<MatchedVolunteer> matchedVolunteers = entry.getValue();
            Organization organization = opportunity.getOrganization();

            if (organization != null) {
                System.out.println("\nOpportunity: " + opportunity.getOpportunityName());
                System.out.println("From Organization: " + organization.getName());
                System.out.println("Score: " + (matchedVolunteers.isEmpty() ? "N/A" : matchedVolunteers.get(0).getScore()));
                System.out.println("Matching Parameters: \n" + MatchingEngine.getMatchingParameters(volunteer, opportunity));
            }
        }
    }
}
