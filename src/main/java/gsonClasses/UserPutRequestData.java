package gsonClasses;

public class UserPutRequestData {
    private int userId;
    private int userLevel;

    public UserPutRequestData() {
    }

    public int getUserId() {
        return userId;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public String toString() {
        return "PutRequestData [ userId: " + userId + ", userLevel: " + userLevel + " ]";
    }
}
