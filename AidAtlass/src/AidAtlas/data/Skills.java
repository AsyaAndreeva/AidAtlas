package AidAtlas.data;

import java.util.HashSet;
import java.util.Set;

public class Skills {
    private Set<String> skillsSet;

    public Skills(Set<String> skillsSet) {
        this.skillsSet = new HashSet<>(skillsSet);
    }

    public Set<String> getSkillsSet() {
        return skillsSet;
    }

    public void setSkillsSet(Set<String> skillsSet) {
        this.skillsSet = new HashSet<>(skillsSet);
    }

    @Override
    public String toString() {
        return String.join(", ", skillsSet);
    }
}
