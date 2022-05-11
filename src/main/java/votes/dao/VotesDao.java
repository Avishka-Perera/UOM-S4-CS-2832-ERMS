package votes.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import gsonClasses.LocationParty;
import gsonClasses.PartyVote;
import gsonClasses.VotePutRequestData;
import location.model.Location;
import user.model.User;
import votes.model.Votes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static connection.Connection.getConnection;

public class VotesDao {

    private static final String SELECT_USER_BY_ID_QUERY = "SELECT * FROM users LEFT JOIN locations ON users.location_id=locations.location_id WHERE users.user_id=?;";
    private static final String SELECT_PARTIES = "SELECT * FROM parties";
    private static final String GET_PARTY_VOTES_BY_ID = "SELECT votes FROM parties WHERE party_id=?";
    private static final String UPDATE_PARTY_VOTES = "UPDATE parties SET votes=? WHERE party_id=?;";

    public Votes getVotes(int userId) throws SQLException {
        Votes returnVote = new Votes();
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatementGetUser = connection.prepareStatement(SELECT_USER_BY_ID_QUERY);
                PreparedStatement preparedStatementGetParties = connection.prepareStatement(SELECT_PARTIES)
        ){
            preparedStatementGetUser.setInt(1,userId);
            ResultSet rs = preparedStatementGetUser.executeQuery();
            if (rs.next()) {

                // User data
                String name = rs.getString("user_name");
                String email = rs.getString("email");
                String contactNumber = rs.getString("contact_number");
                int userLevel = rs.getInt("level");

                // Location data
                Location location = null;
                Integer location_id = rs.getInt("location_id");
                if (rs.wasNull()) location_id = null;
                if (location_id != null) {
                    String location_name = rs.getString("location_name");
                    Integer station_user_id = rs.getInt("station_user_id");
                    if (rs.wasNull()) station_user_id = null;
                    Integer district_center_user_id = rs.getInt("district_center_user_id");
                    if (rs.wasNull()) district_center_user_id = null;
                    int type = rs.getInt("type");
                    location = new Location(location_id, location_name, station_user_id, district_center_user_id, type);

                    // Parties
                    ResultSet rs2 = preparedStatementGetParties.executeQuery();

                    while (rs2.next()) {

                        int partyId = rs2.getInt("party_id");
                        String partyName = rs2.getString("party_name");
                        int vote = 0;

                        String jsonVotes = rs2.getString("votes");
                        if (!rs2.wasNull()) {
                            GsonBuilder builder = new GsonBuilder();
                            builder.setPrettyPrinting();
                            Gson gson = builder.create();
                            List<PartyVote> partyVotes = gson.fromJson(jsonVotes, new TypeToken<List<PartyVote>>(){}.getType());
                            for (PartyVote pv : partyVotes) {
                                int localLocationId = pv.getLocationId();
                                if (localLocationId == location_id) {
                                    vote = pv.getVote();
                                    break;
                                }
                            }
                        }

                        LocationParty lp = new LocationParty(partyId, partyName, vote);
                        returnVote.addLocationParty(lp);
                    }
                }

                User user = new User(userId, name, email, contactNumber, userLevel, location);
                returnVote.setUser(user);
                returnVote.setLocation(location);
                returnVote.setUserName(name);
            }

        }
        return returnVote;
    }

    public int updateVote(VotePutRequestData data) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatementGetPartyVotes = connection.prepareStatement(GET_PARTY_VOTES_BY_ID);
                PreparedStatement preparedStatementUpdateVotes = connection.prepareStatement(UPDATE_PARTY_VOTES)
        ){
            preparedStatementGetPartyVotes.setInt(1, data.getPartyId());
            ResultSet rs = preparedStatementGetPartyVotes.executeQuery();
            if (rs.next()) {
                List<PartyVote> currentVotes = new ArrayList<>();
                String jsonVotes = rs.getString("votes");
                if (!rs.wasNull()) {
                    GsonBuilder builder = new GsonBuilder();
                    builder.setPrettyPrinting();
                    Gson gson = builder.create();
                    currentVotes = gson.fromJson(jsonVotes, new TypeToken<List<PartyVote>>() {}.getType());
                }

                List<PartyVote> newVotes = currentVotes;
                boolean updatedFlag = false;
                for (PartyVote vote :
                        newVotes) {
                    if (vote.getLocationId() == data.getLocationId()) {
                        vote.setVote(data.getVotes());
                        updatedFlag = true;
                        break;
                    }
                }

                // if there was no update, that means there was no data before. Therefore, we'll have to add a new data
                if (!updatedFlag) newVotes.add(new PartyVote(data.getLocationId(), data.getVotes()));

                String newVotesStr = new Gson().toJson(newVotes);
                preparedStatementUpdateVotes.setString(1,newVotesStr);
                preparedStatementUpdateVotes.setInt(2,data.getPartyId());

                return preparedStatementUpdateVotes.executeUpdate();
            } else {
                // no party with that id (usually do not happen)
                return 3;
            }
        }
    }
}
