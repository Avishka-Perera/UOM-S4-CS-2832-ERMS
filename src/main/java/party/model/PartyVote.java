package party.model;

public class PartyVote {
    private int vote;
    private int locationId;

    public PartyVote() {
        this.vote = 0;
        this.locationId = 0;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "PartyVote{" +
                "vote=" + vote +
                ", locationId=" + locationId +
                '}';
    }
}
