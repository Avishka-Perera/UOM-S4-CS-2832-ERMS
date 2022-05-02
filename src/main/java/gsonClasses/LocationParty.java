package gsonClasses;

public class LocationParty {
    private int id;
    private String name;
    private int votes;

    public LocationParty(int id, String name, int votes) {
        this.id = id;
        this.name = name;
        this.votes = votes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getVotes() {
        return votes;
    }

    @Override
    public String toString() {
        return "LocationParty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", votes=" + votes +
                '}';
    }
}
