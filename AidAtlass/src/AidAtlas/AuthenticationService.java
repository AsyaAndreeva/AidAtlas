package AidAtlas;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationService {
    private final Map<String, User> users;

    public AuthenticationService() {
        this.users = new HashMap<>();
    }

    public void registerUser(User user) {
        users.put(user.getEmail(), user);
    }

    public User login(String email, String password) {
        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
