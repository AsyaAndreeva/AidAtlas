package AidAtlas.fileStorage;

import AidAtlas.data.User;
import AidAtlas.data.UserRole;
import AidAtlas.data.UserStorage;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class FileUserStorage implements UserStorage {
    private final String fileName;

    public FileUserStorage(String fileName) {
        this.fileName = fileName;
        clearFile();
    }

    private void clearFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            // Overwrite the file with an empty string
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Set<String> loadUsers() {
        Set<String> users = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    private void saveAllUsers(Set<String> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            for (String user : users) {
                writer.write(user);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(User user) {
        Set<String> users = loadUsers();
        String userLine = user.getName() + ":" + user.getEmail() + ":" + user.getPassword() + ":" + user.getRole();

        // Check for duplicate email and remove if exists
        users.removeIf(line -> line.split(":")[1].equals(user.getEmail()));

        // Add new user line
        users.add(userLine);

        // Save all users
        saveAllUsers(users);
    }

    @Override
    public User getUser(String email) {
        Set<String> users = loadUsers();
        for (String userLine : users) {
            String[] parts = userLine.split(":");
            if (parts.length == 4 && parts[1].equals(email)) {
                String name = parts[0];
                String password = parts[2];
                UserRole role = UserRole.valueOf(parts[3]);
                return new User(name, email, password, role) {};
            }
        }
        return null; // User not found
    }


}
