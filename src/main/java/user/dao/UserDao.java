package user.dao;

import constants.TableData;
import constants.UserLevels;
import location.model.Location;
import user.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static connection.Connection.getConnection;

public class UserDao {

    private static final String INSERT_USERS_QUERY = "INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?);"
            .formatted(TableData.userTable, TableData.Users.name, TableData.Users.email, TableData.Users.password, TableData.Users.contactNumber, TableData.Users.level);
    private static final String SELECT_USER_BY_ID_QUERY = "SELECT * FROM users WHERE user_id=?;";
    private static final String SELECT_USER_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email=?;";
    private static final String SELECT_DISTRICT_USER_QUERY = "SELECT * FROM users LEFT JOIN locations ON users.location_id=locations.location_id WHERE users.level=1";
    private static final String SELECT_STATION_USER_QUERY = "SELECT * FROM users LEFT JOIN locations ON users.location_id=locations.location_id WHERE users.level=0";
    private static final String SELECT_ALL_USERS_QUERY = "SELECT * FROM users LEFT JOIN locations ON users.location_id=locations.location_id;";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE user_id=?;";
    private static final String UPDATE_USER_DATA_QUERY = "UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=?;"
            .formatted(TableData.userTable, TableData.Users.name, TableData.Users.email, TableData.Users.password, TableData.Users.contactNumber, TableData.Users.level, TableData.Users.id);
    private static final String UPDATE_USER_LEVEL_QUERY = "UPDATE users SET level=? WHERE user_id=?;";
    private static final String GET_USER_DATA = "SELECT location_id, level FROM users WHERE user_id=?;";
    private static final String UPDATE_LOCATION_STATION_USER = "UPDATE %s SET %s=? WHERE %s=?;"
            .formatted(TableData.locationTable, TableData.Locations.stationUserId, TableData.Locations.id);
    private static final String UPDATE_LOCATION_DISTRICT_USER = "UPDATE %s SET %s=? WHERE %s=?;"
            .formatted(TableData.locationTable, TableData.Locations.districtUserId, TableData.Locations.id);
    private static final String REMOVE_LOCATION_QUERY = "UPDATE %s SET %s=null WHERE %s=?"
            .formatted(TableData.userTable, TableData.Users.locationId, TableData.Users.id);

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
    public int updateUserLevel(User user) throws SQLException {
        int result;
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_USER_LEVEL_QUERY)
                ) {

            statement.setInt(1,user.getUserLevel());
            statement.setInt(2,user.getId());

            result = statement.executeUpdate();
        }
        return result;
    }

    public int updateUserDetails(User user) {
        int result = 0;
        System.out.println("flag-1, " + user);
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_USER_DATA_QUERY);
                ) {
            System.out.println("flag-2");
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getContactNumber());
            statement.setInt(5, user.getUserLevel());
            statement.setInt(6, user.getId());

            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
                String contactNumber = rs.getString("contact_number");
                int level = rs.getInt("level");

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
                }

                User user;
                if (location != null) user = new User(id, name, email, contactNumber, level, location);
                else user = new User(id, name, email, contactNumber, level);
                users.add(user);
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
        boolean rowDeleted = false;
        try(
                Connection connection = getConnection();
                PreparedStatement deleteUserStatement = connection.prepareStatement(DELETE_USER_QUERY);
                PreparedStatement getUserDataStatement = connection.prepareStatement(GET_USER_DATA);
                PreparedStatement updateLocationStationUserStatement = connection.prepareStatement(UPDATE_LOCATION_STATION_USER);
                PreparedStatement updateLocationDistrictUserStatement = connection.prepareStatement(UPDATE_LOCATION_DISTRICT_USER)
                ) {
            getUserDataStatement.setInt(1, id);
            ResultSet rs = getUserDataStatement.executeQuery();
            if (rs.next()) {
                Integer location_id = rs.getInt("location_id");
                if (rs.wasNull()) location_id = null;

                // update the location if a location is assigned to this user
                if (location_id != null) {
                    int level = rs.getInt("level");
                    // update the location if the user is a district officer or a station officer
                    if (level == UserLevels.STATION_OFFICER_USER_LEVEL || level == UserLevels.DISTRICT_OFFICER_USER_LEVEL) {
                        if (level == UserLevels.STATION_OFFICER_USER_LEVEL) {
                            updateLocationStationUserStatement.setNull(1,Types.INTEGER);
                            updateLocationStationUserStatement.setInt(2, location_id);
                            updateLocationStationUserStatement.executeUpdate();
                        }
                        else {
                            updateLocationDistrictUserStatement.setNull(1,Types.INTEGER);
                            updateLocationDistrictUserStatement.setInt(2, location_id);
                            updateLocationDistrictUserStatement.executeUpdate();
                        }
                    }
                }

                deleteUserStatement.setInt(1,id);
                rowDeleted = deleteUserStatement.executeUpdate() > 0;
            }
        }
        return rowDeleted;
    }

    public boolean removeLocation(Integer id) {
        if (id == null) {
            return true;
        } else {
            try (
                    Connection connection = getConnection();
                    PreparedStatement removeLocationStatement = connection.prepareStatement(REMOVE_LOCATION_QUERY)
                    ) {
                removeLocationStatement.setInt(1,id);
                return removeLocationStatement.executeUpdate() > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
