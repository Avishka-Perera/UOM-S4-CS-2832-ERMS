package votes.dao;

import user.model.User;
import votes.model.Votes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static connection.Connection.getConnection;

public class VotesDao {

    private static final String SELECT_USER_BY_ID_QUERY = "SELECT * FROM users WHERE user_id=?;";

    public Votes getVotes(int userId) throws SQLException {
        Votes returnVote = new Votes();
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatementGetUser = connection.prepareStatement(SELECT_USER_BY_ID_QUERY)
        ){
            // sets the user
            preparedStatementGetUser.setInt(1,userId);
            ResultSet rs = preparedStatementGetUser.executeQuery();
            while (rs.next()) {
                String name = rs.getString("user_name");
                String email = rs.getString("email");
                String contactNumber = rs.getString("contact_number");
                int userLevel = rs.getInt("level");
                returnVote.setUser( new User(userId, name, email, contactNumber, userLevel));
                returnVote.setUserName(name);
            }

            // sets the location

        }
        return returnVote;
    }
}
