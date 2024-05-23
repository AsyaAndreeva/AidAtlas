package AidAtlas;

import java.math.BigDecimal;
import java.util.*;

public class MatchingEngine {

    private static Map<Volunteer, List<MatchedOpportunity>> volunteerMatches = new HashMap<>();
    private static Map<VolunteerOpportunities, List<MatchedVolunteer>> opportunityMatches = new HashMap<>();

    public Map<VolunteerOpportunities, List<MatchedVolunteer>> findMatchesForOpportunities(List<Volunteer> volunteers, List<VolunteerOpportunities> opportunities) {
        Map<VolunteerOpportunities, List<MatchedVolunteer>> matchedOpportunities = new HashMap<>();

        for (VolunteerOpportunities opportunity : opportunities) {
            List<MatchedVolunteer> matchedVolunteers = new ArrayList<>();

            for (Volunteer volunteer : volunteers) {
                int score = calculateMatchingScore(volunteer, opportunity);
                matchedVolunteers.add(new MatchedVolunteer(volunteer, score));
            }

            matchedVolunteers.sort((v1, v2) -> Integer.compare(v2.getScore(), v1.getScore())); // Sort by score descending

            int neededVolunteers = VolunteerOpportunities.getRequiredNumberOfVolunteers();
            int volunteersMatched = Math.min(neededVolunteers, matchedVolunteers.size());

            List<MatchedVolunteer> selectedVolunteers = matchedVolunteers.subList(0, volunteersMatched);
            opportunityMatches.put(opportunity, selectedVolunteers);
            matchedOpportunities.put(opportunity, selectedVolunteers);

            for (MatchedVolunteer matchedVolunteer : selectedVolunteers) {
                volunteerMatches.computeIfAbsent(matchedVolunteer.getVolunteer(), k -> new ArrayList<>()).add(new MatchedOpportunity(opportunity, matchedVolunteer));
            }
        }

        return matchedOpportunities;
    }


    public Map<Volunteer, List<MatchedOpportunity>> findMatchesForVolunteers(List<Volunteer> volunteers, List<VolunteerOpportunities> opportunities) {
        findMatchesForOpportunities(volunteers, opportunities); // This ensures both maps are populated

        return volunteerMatches;
    }

    public void printMatches(List<Volunteer> volunteers, List<VolunteerOpportunities> opportunities) {
        Map<VolunteerOpportunities, List<MatchedVolunteer>> matchedOpportunities = findMatchesForOpportunities(volunteers, opportunities);

        for (VolunteerOpportunities opportunity : matchedOpportunities.keySet()) {
            System.out.println("\nMatching opportunities for " + opportunity.getOppurtunityName() + ":");

            List<MatchedVolunteer> matchedVolunteers = matchedOpportunities.get(opportunity);
            for (MatchedVolunteer matchedVolunteer : matchedVolunteers) {
                Volunteer volunteer = matchedVolunteer.getVolunteer();
                System.out.println("- Volunteer: " + volunteer.getName() +
                        ", Score: " + matchedVolunteer.getScore() +
                        ", Matching Parameters: " + getMatchingParameters(volunteer, opportunity));
            }
        }
    }

    static String getMatchingParametersForOrganization(Organization organization, VolunteerOpportunities opportunity) {
        StringBuilder matchingParameters = new StringBuilder();

        // No skills matching for organizations

        BigDecimal opportunityHours = opportunity.getRequiredWeeklyHours();
        matchingParameters.append("Required Hours: ").append(opportunityHours);

        if (organization.getLocation().equals(opportunity.getLocation())) {
            matchingParameters.append(", Location: ").append(organization.getLocation());
        }

        return matchingParameters.toString();
    }


    public static List<MatchedOpportunity> getMatchedOpportunitiesForVolunteer(Volunteer volunteer) {
        return volunteerMatches.getOrDefault(volunteer, Collections.emptyList());
    }

    public static List<MatchedVolunteer> getMatchedVolunteersForOpportunity(VolunteerOpportunities opportunity) {
        return opportunityMatches.getOrDefault(opportunity, Collections.emptyList());
    }

    public static void printMatchedOpportunitiesForVolunteer(Volunteer volunteer) {
        List<MatchedOpportunity> matchedOpportunities = getMatchedOpportunitiesForVolunteer(volunteer);

        // Sort matched opportunities by score in descending order
        matchedOpportunities.sort((o1, o2) -> Integer.compare(o2.getScore(), o1.getScore()));

        System.out.println("Matched opportunities for " + volunteer.getName() + ":");
        for (MatchedOpportunity matchedOpportunity : matchedOpportunities) {
            VolunteerOpportunities opportunity = matchedOpportunity.getOpportunity();
            MatchedVolunteer matchedVolunteer = matchedOpportunity.getMatchedVolunteer();
            System.out.println("- Opportunity: " + opportunity.getOppurtunityName() +
                    ", Score: " + matchedVolunteer.getScore() +
                    ", Matching Parameters: " + getMatchingParameters(volunteer, opportunity));
        }
    }



    public Map<VolunteerOpportunities, List<MatchedVolunteer>> findMatches(List<Volunteer> volunteers, List<VolunteerOpportunities> opportunities) {
        return findMatchesForOpportunities(volunteers, opportunities);
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
        if (volunteer.getLocation().equalsIgnoreCase(opportunity.getLocation())) {
            score += 5;
        }

        return score;
    }

    static String getMatchingParameters(Volunteer volunteer, VolunteerOpportunities opportunity) {
        StringBuilder matchingParameters = new StringBuilder();

        List<String> volunteerSkills = volunteer.getSkills();
        List<String> opportunitySkills = opportunity.getRequiredSkills();
        matchingParameters.append("    Skills: \n");
        for (String skill : volunteerSkills) {
            if (opportunitySkills.contains(skill)) {
                matchingParameters.append("      \033[32m").append(skill).append("\033[0m\n"); // Green for matched skills
            } else {
                matchingParameters.append("      ").append(skill).append(" (Volunteer)\n");
            }
        }
        for (String skill : opportunitySkills) {
            if (!volunteerSkills.contains(skill)) {
                matchingParameters.append("      ").append(skill).append(" (Opportunity)\n");
            }
        }

        BigDecimal volunteerHours = volunteer.getAvailableHoursWeekly();
        BigDecimal opportunityHours = opportunity.getRequiredWeeklyHours();
        matchingParameters.append("    Available Hours: ");
        if (volunteerHours.compareTo(opportunityHours) >= 0) {
            matchingParameters.append("\033[32m").append(volunteerHours).append("\033[0m\n");
            matchingParameters.append("    Required Hours: \033[32m").append(opportunityHours).append("\033[0m\n");
        } else {
            matchingParameters.append(volunteerHours).append(" (Volunteer)\n");
            matchingParameters.append("    Required Hours: ").append(opportunityHours).append(" (Opportunity)\n");
        }

        if (volunteer.getLocation().equalsIgnoreCase(opportunity.getLocation())) {
            matchingParameters.append("    Matched Location: \033[32m").append(volunteer.getLocation()).append("\033[0m\n");
        } else {
            matchingParameters.append("    Volunteer Location: ").append(volunteer.getLocation()).append("\n");
            matchingParameters.append("    Opportunity Location: ").append(opportunity.getLocation()).append("\n");
        }

        return matchingParameters.toString();
    }
}



