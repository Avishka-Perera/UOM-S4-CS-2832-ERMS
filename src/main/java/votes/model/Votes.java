package votes.model;

import gsonClasses.LocationParty;
import location.model.Location;
import user.model.User;

import java.util.ArrayList;
import java.util.List;

public class Votes {
    private Location location;
    private User user;
    private String userName;
    private final List<LocationParty> parties;

    public Votes() {
        this.parties = new ArrayList<>();
    }

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

    public void addLocationParty(LocationParty locationParty) {
        this.parties.add(locationParty);
    }
}

