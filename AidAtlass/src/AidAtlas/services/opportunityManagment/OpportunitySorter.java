package AidAtlas.services.opportunityManagment;

import AidAtlas.data.MatchedVolunteer;
import AidAtlas.data.VolunteerOpportunities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class OpportunitySorter {
    public List<Map.Entry<VolunteerOpportunities, List<MatchedVolunteer>>> sortMatchesByScore(Map<VolunteerOpportunities, List<MatchedVolunteer>> matches) {
        List<Map.Entry<VolunteerOpportunities, List<MatchedVolunteer>>> sortedMatches = new ArrayList<>(matches.entrySet());
        sortedMatches.sort((entry1, entry2) -> {
            int score1 = entry1.getValue().isEmpty() ? 0 : entry1.getValue().get(0).getScore();
            int score2 = entry2.getValue().isEmpty() ? 0 : entry2.getValue().get(0).getScore();
            return Integer.compare(score2, score1); // Descending order
        });
        return sortedMatches;
    }
}
