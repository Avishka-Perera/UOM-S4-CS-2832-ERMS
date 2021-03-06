package party.model;

import com.google.gson.Gson;
import gsonClasses.PartyVote;

import java.util.List;

public class Party {
    private int id;
    private String name;
    private List<PartyVote> votesList;
    private int votes;

    public Party(int id, String name, List<PartyVote> votes) {
        this.id = id;
        this.name = name;
        this.votesList = votes;
        this.votes = getTotalVotes();
    }

    public Party(int id) {
        this.id = id;
    }

    public Party(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PartyVote> getVotesList() {
        return votesList;
    }

    public String getVoteList() {
        return new Gson().toJson(votesList);
    }

    public void setVotesList(List<PartyVote> votesList) {
        this.votesList = votesList;
        this.votes = getTotalVotes();
    }

    public int getTotalVotes() {
        int total = 0;
        for (PartyVote partyVote :
                votesList) {
            total += partyVote.getVote();
        }
        return total;
    }

    public int getVotes() {
        this.votes = getTotalVotes();
        return votes;
    }

    @Override
    public String toString() {
        return "Party{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", votesList=" + votesList +
                ", votes=" + votes +
                '}';
    }
}