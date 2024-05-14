package AidAtlas;

public class MatchedVolunteer {
    private Volunteer volunteer;
    private int score;

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
