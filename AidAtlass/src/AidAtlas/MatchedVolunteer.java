package AidAtlas;

public class MatchedVolunteer {
    private final Volunteer volunteer;
    private final int score;

    public MatchedVolunteer(Volunteer volunteer, int score) {
        this.volunteer = volunteer;
        this.score = score;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public int getScore() {
        return score;
    }
}
