package AidAtlas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchingEngine {

    public Map<VolunteerOpportunities, List<MatchedVolunteer>> findMatches(List<Volunteer> volunteers, List<VolunteerOpportunities> opportunities) {
        System.out.println("\nMatching volunteers to opportunities:");

        Map<VolunteerOpportunities, List<MatchedVolunteer>> matchedOpportunities = new HashMap<>();

        for (VolunteerOpportunities opportunity : opportunities) {
            System.out.println("\nMatching for opportunity: " + opportunity.getOppurtunityName());
            List<MatchedVolunteer> matchedVolunteers = new ArrayList<>();

            for (Volunteer volunteer : volunteers) {
                int score = calculateMatchingScore(volunteer, opportunity);
                matchedVolunteers.add(new MatchedVolunteer(volunteer, score));
            }

            matchedVolunteers.sort((v1, v2) -> Integer.compare(v2.getScore(), v1.getScore())); // Sort by score descending

            int neededVolunteers = opportunity.getRequiredNumberOfVolunteers();
            int volunteersMatched = Math.min(neededVolunteers, matchedVolunteers.size());

            List<MatchedVolunteer> selectedVolunteers = matchedVolunteers.subList(0, volunteersMatched);
            matchedOpportunities.put(opportunity, selectedVolunteers);

            for (MatchedVolunteer matchedVolunteer : selectedVolunteers) {
                System.out.println("- Volunteer: " + matchedVolunteer.getVolunteer().getName() +
                        ", Score: " + matchedVolunteer.getScore() +
                        ", Matching Parameters: " + getMatchingParameters(matchedVolunteer.getVolunteer(), opportunity));
            }

            if (volunteersMatched < neededVolunteers) {
                int moreNeeded = neededVolunteers - volunteersMatched;
                System.out.println("- " + moreNeeded + " more volunteer(s) needed for " + opportunity.getOppurtunityName());
            }
        }

        return matchedOpportunities;
    }

    public int calculateMatchingScore(Volunteer volunteer, VolunteerOpportunities opportunity) {
        int score = 0;

        // Calculate score based on shared skills
        List<String> volunteerSkills = volunteer.getSkills();
        List<String> opportunitySkills = opportunity.getRequiredSkills();
        for (String skill : volunteerSkills) {
            if (opportunitySkills.contains(skill)) {
                score += 3; // Higher weight for skills
            }
        }

        // Calculate score based on available hours
        BigDecimal volunteerHours = volunteer.getAvailableHoursWeekly();
        BigDecimal opportunityHours = opportunity.getRequiredWeeklyHours();
        if (volunteerHours.compareTo(opportunityHours) >= 0) {
            score += 2; // Volunteer can fulfill required hours
        }

        // Add score based on location (you can adjust this based on specific criteria)
        // For example, if the organization is in the same city as the volunteer, add more score
        if (volunteer.getLocation().equals(opportunity.getOrganization().getLocation())) {
            score += 5;
        }

        return score;
    }

    public void printMatches(List<Volunteer> volunteers, List<VolunteerOpportunities> opportunities) {
        Map<VolunteerOpportunities, List<MatchedVolunteer>> matchedOpportunities = findMatches(volunteers, opportunities);

        // Print matched volunteers under each opportunity
        for (VolunteerOpportunities opportunity : matchedOpportunities.keySet()) {
            System.out.println("\nMatching opportunities for " + opportunity.getOppurtunityName() + ":");

            List<MatchedVolunteer> matchedVolunteers = matchedOpportunities.get(opportunity);
            for (MatchedVolunteer matchedVolunteer : matchedVolunteers) {
                System.out.println("- Volunteer: " + matchedVolunteer.getVolunteer().getName() +
                        ", Score: " + matchedVolunteer.getScore() +
                        ", Matching Parameters: " + getMatchingParameters(matchedVolunteer.getVolunteer(), opportunity));
            }
        }

        // Print summary of remaining needed volunteers
        for (VolunteerOpportunities opportunity : matchedOpportunities.keySet()) {
            int neededVolunteers = opportunity.getRequiredNumberOfVolunteers();
            List<MatchedVolunteer> matchedVolunteers = matchedOpportunities.get(opportunity);
            int volunteersMatched = matchedVolunteers.size();

            if (volunteersMatched < neededVolunteers) {
                int moreNeeded = neededVolunteers - volunteersMatched;
                System.out.println("- " + moreNeeded + " more volunteer(s) needed for " + opportunity.getOppurtunityName());
            }
        }
    }

    private String getMatchingParameters(Volunteer volunteer, VolunteerOpportunities opportunity) {
        StringBuilder matchingParameters = new StringBuilder();

        List<String> volunteerSkills = volunteer.getSkills();
        List<String> opportunitySkills = opportunity.getRequiredSkills();
        matchingParameters.append("Skills: ");
        for (String skill : volunteerSkills) {
            if (opportunitySkills.contains(skill)) {
                matchingParameters.append(skill).append(", ");
            }
        }
        if (!matchingParameters.isEmpty()) {
            matchingParameters.delete(matchingParameters.length() - 2, matchingParameters.length()); // Remove trailing comma and space
        }

        BigDecimal volunteerHours = volunteer.getAvailableHoursWeekly();
        BigDecimal opportunityHours = opportunity.getRequiredWeeklyHours();
        matchingParameters.append(", Available Hours: ").append(volunteerHours);
        matchingParameters.append(", Required Hours: ").append(opportunityHours);

        if (volunteer.getLocation().equals(opportunity.getOrganization().getLocation())) {
            matchingParameters.append(", Location: ").append(volunteer.getLocation());
        }

        return matchingParameters.toString();
    }
}
