package user.dao;

import user.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static connection.Connection.getConnection;

public class UserDao {

    private static final String INSERT_USERS_QUERY = "INSERT INTO users (user_name, email, password, contact_number, level) VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT_USER_BY_ID_QUERY = "SELECT * FROM users WHERE user_id=?;";
    private static final String SELECT_USER_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email=?;";
    private static final String SELECT_DISTRICT_USER_QUERY = "SELECT * FROM users WHERE level=1";
    private static final String SELECT_STATION_USER_QUERY = "SELECT * FROM users WHERE level=0";
    private static final String SELECT_ALL_USERS_QUERY = "SELECT * FROM users;";
    private static final String DELETE_USER_QUERY = "DELETE FROM USERS WHERE user_id=?;";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET level=? WHERE user_id=?;";

    // create new user
    public int[] addUser(User user) throws ClassNotFoundException {
        int result = 0;
        int id = 0;
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatementCheck = connection.prepareStatement(SELECT_USER_BY_EMAIL_QUERY);
                PreparedStatement preparedStatementUpdate = connection.prepareStatement(INSERT_USERS_QUERY)
        ){
            preparedStatementCheck.setString(1,user.getEmail());
            ResultSet rs = preparedStatementCheck.executeQuery();

            int occerences = 0;
            while (rs.next()) {
                occerences++;
            }

            if (occerences == 0) {
                // add the user
                preparedStatementUpdate.setString(1, user.getName());
                preparedStatementUpdate.setString(2, user.getEmail());
                preparedStatementUpdate.setString(3, user.getPassword());
                preparedStatementUpdate.setString(4, user.getContactNumber());
                preparedStatementUpdate.setInt(5,user.getUserLevel());
                result = preparedStatementUpdate.executeUpdate();

                // get the id of the created user
                ResultSet rs2 = preparedStatementCheck.executeQuery();
                while (rs2.next()) {
                    id = rs2.getInt("user_id");
                }

            } else {
                result = 3;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new int[]{result, id};
    }

    // update user
    public boolean updateUser(User user) throws SQLException {
        boolean result;
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY)
                ) {

            statement.setInt(1,user.getUserLevel());
            statement.setInt(2,user.getId());

            result = statement.executeUpdate() > 0;
        }
        return result;
    }

    // select user by id
    public User selectUser(int id) {
        User user = null;
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID_QUERY)
        ) {
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                //name=?, email=?, password=?, contact_number=?, level=?
                String name = rs.getString("user_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String contactNumber = rs.getString("contact_number");
                int level = rs.getInt("level");

                user = new User(name, email, password, contactNumber, level);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // selects the users under the below-mentioned conditions
    protected List<User> selectUsersConditionally(int cond) {
        //  cond: Condition
        //      a) 0: Select all users
        //      b) 1: Select station users
        //      c) 2: Select district users
        String query = SELECT_ALL_USERS_QUERY;
        if (cond == 1) query = SELECT_STATION_USER_QUERY;
        if (cond == 2) query = SELECT_DISTRICT_USER_QUERY;

        List<User> users = new ArrayList<>();
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("user_id");
                String name = rs.getString("user_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String contactNumber = rs.getString("contact_number");
                int level = rs.getInt("level");

                users.add(new User(id, name, email, password, contactNumber, level));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // select users
    public List<User> selectAllUsers() {
        return selectUsersConditionally(0);
    }

    // select all station users
    public List<User> selectStationUsers() {
        return selectUsersConditionally(1);
    }

    // select all district users
    public List<User> selectDistrictUsers() {
        return selectUsersConditionally(2);
    }

    // delete user
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try(
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_USER_QUERY)
                ) {
            statement.setInt(1,id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
}
