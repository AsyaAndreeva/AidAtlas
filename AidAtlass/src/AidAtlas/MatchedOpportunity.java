package AidAtlas;

public class MatchedOpportunity {
    private VolunteerOpportunities opportunity;
    private MatchedVolunteer matchedVolunteer;

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
