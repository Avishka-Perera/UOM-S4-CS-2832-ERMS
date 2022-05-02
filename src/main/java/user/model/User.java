package user.model;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String contactNumber;
    private final int userLevel;
    private Integer locationId;

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

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }
}
