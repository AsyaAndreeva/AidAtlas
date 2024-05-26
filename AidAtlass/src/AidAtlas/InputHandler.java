package AidAtlas;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;

public class InputHandler {
    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public int getIntInput(String prompt) {
        int value = 0;
        while (true) {
            try {
                System.out.print(prompt + ": ");
                value = Integer.parseInt(scanner.nextLine().trim());
                break; // Break the loop if conversion is successful
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
            }
        }
        return value;
    }

    public String getStringInput(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine().trim();
    }

    // Method to get multiple skills/interests/preferred types of work
    public List<String> getListInput(String type) {
        List<String> skillsList = new ArrayList<>();
        System.out.println("Enter " + type + " (comma-separated, type 'done' when finished): ");
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("done")) {
                break;
            }
            try {
                // Split input by comma and add to the list
                skillsList.addAll(Arrays.asList(input.split("\\s*,\\s*")));
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter the " + type + " correctly.");
            }
        }
        return skillsList;
    }

    public BigDecimal getBigDecimalInput(String prompt) {
        BigDecimal value = null;
        while (true) {
            try {
                System.out.print(prompt + ": ");
                String input = scanner.nextLine().trim();
                value = new BigDecimal(input);
                break; // Break the loop if conversion is successful
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
        return value;
    }
}
