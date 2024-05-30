package AidAtlas.services.profileManagment;

import AidAtlas.data.Organization;
import AidAtlas.data.User;
import AidAtlas.data.UserRole;
import AidAtlas.data.Volunteer;
import AidAtlas.services.navigation.MainNavigation;
import AidAtlas.services.navigation.ShowOrganizationMenu;
import AidAtlas.services.navigation.ShowVolunteerMenu;

import java.util.Scanner;

public class RoleSpecificNavigator {

    public static void navigateToRoleSpecificMenu(Scanner scanner, ProfileManagement profileManagement, User newUser, UserRole role) {
        MainNavigation mainNav = new MainNavigation(profileManagement, scanner);
        if (role == UserRole.VOLUNTEER) {
            ShowVolunteerMenu.showVolunteerMenu(scanner, (Volunteer) newUser, profileManagement);
        } else if (role == UserRole.ORGANIZATION) {
            ShowOrganizationMenu.showOrganizationMenu(scanner, (Organization) newUser, profileManagement);
        }
    }
}
