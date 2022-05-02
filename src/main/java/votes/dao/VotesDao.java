package votes.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import gsonClasses.PartyVote;
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

    private static final String SELECT_USER_BY_ID_QUERY = "SELECT * FROM users WHERE user_id=?;";
    private static final String SELECT_LOCATION_BY_ID = "SELECT * FROM locations WHERE location_id=?";
    private static final String SELECT_PARTIES = "SELECT * FROM parties";

    public Votes getVotes(int userId) throws SQLException {
        Votes returnVote = new Votes();
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatementGetUser = connection.prepareStatement(SELECT_USER_BY_ID_QUERY);
                PreparedStatement preparedStatementGetLocation = connection.prepareStatement(SELECT_LOCATION_BY_ID);
                PreparedStatement preparedStatementGetParties = connection.prepareStatement(SELECT_PARTIES)
        ){
            preparedStatementGetUser.setInt(1,userId);
            ResultSet rs1 = preparedStatementGetUser.executeQuery();
            if (rs1.next()) {

                // sets the user
                String name = rs1.getString("user_name");
                String email = rs1.getString("email");
                String contactNumber = rs1.getString("contact_number");
                Integer locationId = rs1.getInt("location_id");
                if (rs1.wasNull()) locationId = null;
                int userLevel = rs1.getInt("level");
                returnVote.setUser(new User(userId, name, email, contactNumber, userLevel, locationId));
                returnVote.setUserName(name);

                // sets the location
                if (locationId != null) {
                    preparedStatementGetLocation.setInt(1,locationId);
                    ResultSet rs2 = preparedStatementGetLocation.executeQuery();
                    if (rs2.next()) {
                        String locationName = rs2.getString("location_name");
                        Integer stationUserId = rs2.getInt("station_user_id");
                        if (rs2.wasNull()) stationUserId = null;
                        Integer districtCenterUserId = rs2.getInt("district_center_user_id");
                        if (rs2.wasNull()) districtCenterUserId = null;
                        int type = rs2.getInt("type");
                        returnVote.setLocation(new Location(locationId, locationName, stationUserId, districtCenterUserId, type));

                        // sets the parties
                        ResultSet rs3 = preparedStatementGetParties.executeQuery();
                        while (rs3.next()) {
                            String jsonVotes = rs3.getString("votes");
                            if (!rs3.wasNull()) {
                                List<PartyVote> partyVotes;
                                if (jsonVotes != null) {
                                    GsonBuilder builder = new GsonBuilder();
                                    builder.setPrettyPrinting();
                                    Gson gson = builder.create();
                                    partyVotes = gson.fromJson(jsonVotes, new TypeToken<List<PartyVote>>(){}.getType());
                                    for (PartyVote pv : partyVotes) {
                                        int localLocationId = pv.getLocationId();
                                        if (localLocationId == locationId) {
                                            returnVote.addParty(rs3.getInt("party_id"), rs3.getString("party_name"), pv.getVote());
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        System.out.println(returnVote.getParties().get(0));
                    }
                } else returnVote.setLocation(null);
            }

        }
        return returnVote;
    }
}
