package party.dao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ibm.db2.jcc.DB2ResultSet;
import com.ibm.db2.jcc.json.DB2JSONResultSet;
import location.model.Location;
import party.model.Party;
import party.model.PartyVote;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static connection.Connection.getConnection;
import static utilities.Utilities.jsonStringToObj;

public class PartyDao {

    private static final String INSERT_PARTY_QUERY = "INSERT INTO parties (party_name) VALUES (?);";
    private static final String SELECT_ALL_PARTIES_QUERY = "SELECT * FROM parties;";
    private static final String SELECT_PARTY_BY_ID_QUERY = "SELECT * FROM parties WHERE id=?;";
    private static final String SELECT_PARTY_BY_NAME_QUERY = "SELECT * FROM parties WHERE party_name=? LIMIT 1;";
    private static final String SELECT_LAST_RECORD_QUERY = "SELECT * FROM parties ORDER BY party_id DESC LIMIT 1";
    private static final String DELETE_PARTY_QUERY = "DELETE FROM parties WHERE party_id=?;";
    private static final String UPDATE_PARTY_QUERY = "UPDATE parties SET station_user_id=?, district_center_user_id=?, type=? WHERE id=?;";

    // select all locations
    public List<Party> selectAllParties() {
        List<Party> parties = new ArrayList<>();
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PARTIES_QUERY)
        ) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("party_id");
                String name = rs.getString("party_name");
                String jsonVotes = rs.getString("votes");

                List<PartyVote> votes = new ArrayList<>();
                if (jsonVotes != null) {
                    GsonBuilder builder = new GsonBuilder();
                    builder.setPrettyPrinting();
                    Gson gson = builder.create();
                    votes = gson.fromJson(jsonVotes, new TypeToken<List<PartyVote>>(){}.getType());
                }

                parties.add(new Party(id, name, votes));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parties;
    }

    // create new party
    public int[] addParty(Party party) throws ClassNotFoundException {
        int result = 0;
        int id = 0;
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatementCheck = connection.prepareStatement(SELECT_PARTY_BY_NAME_QUERY);
                PreparedStatement preparedStatementAdd = connection.prepareStatement(INSERT_PARTY_QUERY);
                PreparedStatement preparedStatementLastRow = connection.prepareStatement(SELECT_LAST_RECORD_QUERY)
        ){
            // Check if there are locations with the same name
            preparedStatementCheck.setString(1, party.getName());
            ResultSet rs1 = preparedStatementCheck.executeQuery();
            int count = 0;
            if (rs1.next()) {
                count++;
            }

            if (count == 0) {
                preparedStatementAdd.setString(1, party.getName());
                result = preparedStatementAdd.executeUpdate();

                // get the id of the created party
                ResultSet rs2 = preparedStatementLastRow.executeQuery();
                while (rs2.next()) {
                    id = rs2.getInt("party_id");
                }
            } else {
                result = 3;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new int[]{result, id};
    }

    // delete party
    public boolean deleteParty(int id) throws SQLException {
        boolean rowDeleted;
        try(
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_PARTY_QUERY)
        ) {
            statement.setInt(1,id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
}