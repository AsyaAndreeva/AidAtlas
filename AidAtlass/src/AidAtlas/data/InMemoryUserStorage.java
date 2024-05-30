package AidAtlas.data;

import AidAtlas.fileStorage.FileUserStorage;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserStorage implements UserStorage {
    protected final Map<String, User> users = new HashMap<>();

    private final FileUserStorage fileUserStorage = new FileUserStorage("users_credentials.txt");

    @Override
    public void saveUser(User user) {
        users.put(user.getEmail(), user);
        fileUserStorage.saveUser(user);
    }

    @Override
    public User getUser(String email) {
        User user = users.get(email);
        if (user == null) {
            user = fileUserStorage.getUser(email);
            if (user != null) {
                users.put(email, user);
            }
        }
        return user;
    }
}
