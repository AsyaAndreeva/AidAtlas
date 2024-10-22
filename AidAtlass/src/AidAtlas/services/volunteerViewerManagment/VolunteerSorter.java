package AidAtlas.services.volunteerViewerManagment;

import AidAtlas.data.MatchedVolunteer;
import AidAtlas.data.VolunteerOpportunities;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VolunteerSorter {
    public List<MatchedVolunteer> sortVolunteersByScore(List<MatchedVolunteer> volunteers) {
        return volunteers.stream()
                .sorted(Comparator.comparingInt(MatchedVolunteer::getScore).reversed())
                .collect(Collectors.toList());
    }
}
