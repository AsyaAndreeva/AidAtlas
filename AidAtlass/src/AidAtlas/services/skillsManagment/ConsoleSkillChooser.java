package AidAtlas.services.skillsManagment;

import AidAtlas.data.SkillProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleSkillChooser implements ChooseSkills {
    @Override
    public List<String> chooseSkills() {
        displayAvailableSkills();
        String[] selectedSkillsIndices = getUserInput();
        return getSelectedSkills(selectedSkillsIndices);
    }

    private void displayAvailableSkills() {
        System.out.println("Choose from the following skills:");
        int index = 1;
        for (String skill : SkillProvider.PREDEFINED_SKILLS) {
            System.out.println(index + ". " + skill);
            index++;
        }
    }

    private String[] getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the numbers corresponding to the skills you want (comma-separated): ");
        return scanner.nextLine().split("\\s*,\\s*");
    }

    private List<String> getSelectedSkills(String[] selectedSkillsIndices) {
        List<String> selectedSkills = new ArrayList<>();
        for (String indexStr : selectedSkillsIndices) {
            int selectedIndex = parseIndex(indexStr);
            if (isValidIndex(selectedIndex)) {
                selectedSkills.add(getSkillAtIndex(selectedIndex));
            } else {
                logInvalidIndex(selectedIndex);
            }
        }
        return selectedSkills;
    }

    private int parseIndex(String indexStr) {
        try {
            return Integer.parseInt(indexStr);
        } catch (NumberFormatException e) {
            logInvalidIndexFormat(indexStr);
            return -1; // Return an invalid index
        }
    }

    private boolean isValidIndex(int index) {
        return index >= 1 && index <= SkillProvider.PREDEFINED_SKILLS.size();
    }

    private String getSkillAtIndex(int index) {
        return (String) SkillProvider.PREDEFINED_SKILLS.toArray()[index - 1];
    }

    private void logInvalidIndex(int index) {
        System.out.println("Invalid skill index: " + index);
    }

    private void logInvalidIndexFormat(String indexStr) {
        System.out.println("Invalid index format: " + indexStr);
    }
}
