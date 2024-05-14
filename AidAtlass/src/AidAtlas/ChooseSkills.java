package AidAtlas;

import java.util.*;

interface ChooseSkills {
    Set<String> PredefinedSkills = new HashSet<>(Arrays.asList(
            "Programming", "Teaching", "Writing", "Design", "Marketing", "Research", "Cooking", "Driving"));

    default List<String> chooseSkills() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose from the following skills:");
        int index = 1;
        for (String skill : PredefinedSkills) {
            System.out.println(index + ". " + skill);
            index++;
        }
        System.out.println("Enter the numbers corresponding to the skills you want (comma-separated): ");
        String[] selectedSkillsIndices = scanner.nextLine().split("\\s*,\\s*");
        List<String> selectedSkills = new ArrayList<>();
        for (String indexStr : selectedSkillsIndices) {
            int selectedIndex = Integer.parseInt(indexStr);
            if (selectedIndex >= 1 && selectedIndex <= PredefinedSkills.size()) {
                selectedSkills.add((String) PredefinedSkills.toArray()[selectedIndex - 1]);
            } else {
                System.out.println("Invalid skill index: " + selectedIndex);
            }
        }
        return selectedSkills;
    }
}