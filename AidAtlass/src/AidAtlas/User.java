package AidAtlas;

abstract public class User{

    String name;
    String email;
    String password;
    private final UserRole role;

    public User(String name, String email, String password, UserRole role) { // Constructor modification
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters and setters
    public UserRole getRole() {
        return role;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
