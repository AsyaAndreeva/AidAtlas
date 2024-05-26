// test/AidAtlas/MatchingEngineTest.java
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import AidAtlas.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MatchingEngineTest {
    private MatchingEngine matchingEngine;
    private List<Volunteer> volunteers;
    private List<VolunteerOpportunities> opportunities;

    @Before
    public void setUp() {
        matchingEngine = new MatchingEngine();
        matchingEngine.clearState(); // Ensure previous state is cleared

        // Creating Volunteers
        volunteers = new ArrayList<>();
        volunteers.add(new Volunteer("John Doe", "john@example.com", "password", Arrays.asList("Programming", "Teaching"), new BigDecimal("10"), new BigDecimal("100"), "CityA"));
        volunteers.add(new Volunteer("Jane Smith", "jane@example.com", "password", Arrays.asList("Writing", "Design"), new BigDecimal("5"), new BigDecimal("50"), "CityB"));
        volunteers.add(new Volunteer("Emily Davis", "emily@example.com", "password", Arrays.asList("Marketing", "Research"), new BigDecimal("8"), new BigDecimal("80"), "CityA"));

        // Creating an Organization
        Organization org1 = new Organization("Org1", "org1@example.com", "password", "Helping the community", "CityA", null, null);

        // Creating Opportunities
        opportunities = new ArrayList<>();
        opportunities.add(new VolunteerOpportunities("Opportunity1", "CityA", org1, Arrays.asList("Programming", "Writing"), new BigDecimal("10"), 2));
        opportunities.add(new VolunteerOpportunities("Opportunity2", "CityB", org1, Arrays.asList("Design", "Teaching"), new BigDecimal("5"), 1));
    }

    @Test
    public void testFindMatchesForOpportunities() {
        Map<VolunteerOpportunities, List<MatchedVolunteer>> matches = matchingEngine.findMatchesForOpportunities(volunteers, opportunities);

        // Ensure correct number of opportunities are matched
        assertEquals(2, matches.size());

        // Check Opportunity 1
        List<MatchedVolunteer> matchesForOpportunity1 = matches.get(opportunities.get(0));
        assertEquals(1, matchesForOpportunity1.size());
        assertEquals("John Doe", matchesForOpportunity1.get(0).getVolunteer().getName());

        // Check Opportunity 2
        List<MatchedVolunteer> matchesForOpportunity2 = matches.get(opportunities.get(1));
        assertEquals(1, matchesForOpportunity2.size());
        assertEquals("Jane Smith", matchesForOpportunity2.get(0).getVolunteer().getName());
    }

    @Test
    public void testCalculateMatchingScore() {
        Volunteer volunteer = new Volunteer("Test Volunteer", "test@example.com", "password", Arrays.asList("Programming", "Writing"), new BigDecimal("10"), new BigDecimal("100"), "CityA");
        Organization testOrg = new Organization("Test Org", "testorg@example.com", "password", "Community Support", "CityA", null, null);
        VolunteerOpportunities opportunity = new VolunteerOpportunities("Test Opportunity", "CityA", testOrg, Arrays.asList("Programming", "Writing"), new BigDecimal("10"), 2);

        int score = matchingEngine.calculateMatchingScore(volunteer, opportunity);
        assertEquals(13, score); // 6 for skills, 2 for hours, 5 for location
    }
}
