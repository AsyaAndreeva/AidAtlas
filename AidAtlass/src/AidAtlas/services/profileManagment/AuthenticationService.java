package AidAtlas.services.profileManagment;

import AidAtlas.data.User;
import AidAtlas.data.UserStorage;
import AidAtlas.fileStorage.FileUserStorage;

public class AuthenticationService {
    private static UserStorage userStorage;

    public AuthenticationService(UserStorage userStorage) {
        AuthenticationService.userStorage = userStorage;
    }

    public static void registerUser(User user) {
        userStorage.saveUser(user);
    }

    public User login(String email, String password) {
        User user = userStorage.getUser(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}