package AidAtlas.data;

import AidAtlas.data.User;

public interface UserStorage {
    void saveUser(User user);
    User getUser(String email);
}
