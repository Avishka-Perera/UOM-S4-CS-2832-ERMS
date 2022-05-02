package votes.model;

import location.model.Location;
import user.model.User;

import java.util.List;

public class Votes {
    private Location location;
    private User user;
    private String userName;
    private List<LocationParty> parties;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<LocationParty> getParties() {
        return parties;
    }

    public void setParties(List<LocationParty> parties) {
        this.parties = parties;
    }
}

class LocationParty {
    private int id;
    private String name;
    private int votes;
}
