package AidAtlas.services.volunteerViewerManagment;

import AidAtlas.data.Volunteer;
import AidAtlas.services.profileManagment.ProfileManagement;

import java.util.List;

public class VolunteerFetcher {
    private final ProfileManagement profileManagement;

    public VolunteerFetcher(ProfileManagement profileManagement) {
        this.profileManagement = profileManagement;
    }

    public List<Volunteer> fetchAllVolunteers() {
        return profileManagement.getAllVolunteers();
    }
}
