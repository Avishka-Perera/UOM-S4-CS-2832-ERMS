package user.model;

import location.model.Location;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String contactNumber;
    private final int userLevel;
    private Location location;

    public User(int id, String name, String email, String contactNumber, int userLevel, Location location) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.userLevel = userLevel;
        this.location = location;
    }

    public User(int id, String name, String email, String password, String contactNumber, int userLevel) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.userLevel = userLevel;
    }
    public User(String name, String email, String password, String contactNumber, int userLevel) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.userLevel = userLevel;
    }

    public User(int id, String name, String email, String contactNumber, int userLevel) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.userLevel = userLevel;
    }

    public User(int id, int userLevel) {
        this.id = id;
        this.userLevel = userLevel;
    }

    public int getId() {
        return id;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
