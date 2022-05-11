package gsonClasses;

public class VotePutRequestData {
    private int partyId;
    private int locationId;
    private int votes;

    public VotePutRequestData(int partyId, int locationId, int votes) {
        this.partyId = partyId;
        this.locationId = locationId;
        this.votes = votes;
    }

    public int getPartyId() {
        return partyId;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "VotePutRequestData{" +
                "partyId=" + partyId +
                ", locationId=" + locationId +
                ", votes=" + votes +
                '}';
    }
}
