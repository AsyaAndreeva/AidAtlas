package AidAtlas;

public class MatchedOpportunity {
    private final VolunteerOpportunities opportunity;
    private final MatchedVolunteer matchedVolunteer;

    public MatchedOpportunity(VolunteerOpportunities opportunity, MatchedVolunteer matchedVolunteer) {
        this.opportunity = opportunity;
        this.matchedVolunteer = matchedVolunteer;
    }

    public VolunteerOpportunities getOpportunity() {
        return opportunity;
    }

    public MatchedVolunteer getMatchedVolunteer() {
        return matchedVolunteer;
    }

    // Updated method to get the score from the matched volunteer
    public int getScore() {
        return matchedVolunteer.getScore();
    }
}
