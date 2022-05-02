package location.dao;
import location.model.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static connection.Connection.getConnection;

public class LocationDao {

    private static final String INSERT_LOCATION_QUERY = "INSERT INTO locations (location_name, type) VALUES (?, ?);";
    private static final String SELECT_ALL_LOCATIONS_QUERY = "SELECT * FROM locations;";
    private static final String SELECT_LOCATION_BY_ID_QUERY = "SELECT * FROM locations WHERE location_id=?;";
    private static final String SELECT_LOCATION_BY_NAME_QUERY = "SELECT * FROM locations WHERE location_name=? LIMIT 1;";
    private static final String SELECT_LAST_RECORD_QUERY = "SELECT * FROM locations ORDER BY location_id DESC LIMIT 1";
    private static final String DELETE_LOCATION_QUERY = "DELETE FROM locations WHERE location_id=?;";
    private static final String UPDATE_LOCATION_QUERY = "UPDATE locations SET station_user_id=?, district_center_user_id=?, type=? WHERE location_id=?;";
    private static final String GET_LOCATION_USERS_QUERY = "SELECT station_user_id, district_center_user_id FROM locations WHERE location_id=?";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET location_id=? WHERE user_id=?";
    private static final String SELECT_USER_QUERY = "SELECT location_id, user_id FROM users WHERE user_id=?";

    // select all locations
    public List<Location> selectAllLocations() {
        List<Location> locations = new ArrayList<>();
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_LOCATIONS_QUERY)
        ) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("location_id");
                String name = rs.getString("location_name");
                Integer stationUserId = rs.getInt("station_user_id");
                if (rs.wasNull()) stationUserId = null;
                Integer districtCenterUserId = rs.getInt("district_center_user_id");
                if (rs.wasNull()) districtCenterUserId = null;
                int type = rs.getInt("type");
                locations.add(new Location(id, name, stationUserId, districtCenterUserId, type));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locations;
    }

    // select location by id
    public Location selectLocation(int id) {
        Location location = null;
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_LOCATION_BY_ID_QUERY)
        ) {
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("location_name");
                int stationUserId = rs.getInt("station_user_id");
                int districtCenterUserId = rs.getInt("district_center_user_id");
                int type = rs.getInt("type");

                location = new Location(id, name, stationUserId, districtCenterUserId, type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return location;
    }

    // create new location
    public int[] addLocation(Location location) throws ClassNotFoundException {
        int result = 0;
        int id = 0;
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatementCheck = connection.prepareStatement(SELECT_LOCATION_BY_NAME_QUERY);
                PreparedStatement preparedStatementAdd = connection.prepareStatement(INSERT_LOCATION_QUERY);
                PreparedStatement preparedStatementLastRow = connection.prepareStatement(SELECT_LAST_RECORD_QUERY)
        ){
            // Check if there are locations with the same name
            preparedStatementCheck.setString(1, location.getName());
            ResultSet rs1 = preparedStatementCheck.executeQuery();
            int count = 0;
            if (rs1.next()) {
                count++;
            }

            if (count == 0) {
                preparedStatementAdd.setString(1, location.getName());
                preparedStatementAdd.setInt(2, location.getType());
                result = preparedStatementAdd.executeUpdate();

                // get the id of the created location
                ResultSet rs2 = preparedStatementLastRow.executeQuery();
                while (rs2.next()) {
                    id = rs2.getInt("location_id");
                }
            } else {
                result = 3;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new int[]{result, id};
    }

    // update location
    public int updateLocation(Location location) throws SQLException {
        int result = 0;
        try (
                Connection connection = getConnection();
                PreparedStatement locationCheckStatement = connection.prepareStatement(GET_LOCATION_USERS_QUERY);
                PreparedStatement locationUpdateStatement = connection.prepareStatement(UPDATE_LOCATION_QUERY);
                PreparedStatement newStationUserCheckStatement = connection.prepareStatement(SELECT_USER_QUERY);
                PreparedStatement newDistrictUserCheckStatement = connection.prepareStatement(SELECT_USER_QUERY);
                PreparedStatement newStationUserUpdateStatement = connection.prepareStatement(UPDATE_USER_QUERY);
                PreparedStatement newDistrictUserUpdateStatement = connection.prepareStatement(UPDATE_USER_QUERY);
                PreparedStatement oldStationUserUpdateStatement = connection.prepareStatement(UPDATE_USER_QUERY);
                PreparedStatement oldDistrictUserUpdateStatement = connection.prepareStatement(UPDATE_USER_QUERY)
        ) {
            // check if the users are available
            int occurrencesStationUser = 0;
            int occurrencesDistrictUser = 0;
            if (location.getStationUserId() != null) {
                newStationUserCheckStatement.setInt(1,location.getStationUserId());
                ResultSet rs1 = newStationUserCheckStatement.executeQuery();
                if (rs1.next()) if (rs1.getString("location_id")!=null) {
                    occurrencesStationUser++;
                    System.out.println("station user not available");
                }
            }
            if (location.getDistrictCenterUserId() != null) {
                newDistrictUserCheckStatement.setInt(1,location.getDistrictCenterUserId());
                ResultSet rs2 = newDistrictUserCheckStatement.executeQuery();
                if (rs2.next()) if (rs2.getString("location_id")!=null) {
                    occurrencesDistrictUser++;
                    System.out.println("district user not available");
                }
            }

            // get the previous station user and district user
            locationCheckStatement.setInt(1, location.getId());
            ResultSet rs3 = locationCheckStatement.executeQuery();
            Integer oldStationUserId = null;
            Integer oldDistrictCenterUserId = null;
            Integer newStationUserId = location.getStationUserId();
            Integer newDistrictCenterUserId = location.getDistrictCenterUserId();
            if (rs3.next()) {
                oldStationUserId = rs3.getInt("station_user_id");
                if (rs3.wasNull()) oldStationUserId = null;
                oldDistrictCenterUserId = rs3.getInt("district_center_user_id");
                if (rs3.wasNull()) oldDistrictCenterUserId = null;
            }

            boolean stationUserOk = occurrencesStationUser == 0 || Objects.equals(newStationUserId, oldStationUserId);
            boolean districtUserOk = occurrencesDistrictUser == 0 || Objects.equals(newDistrictCenterUserId, oldDistrictCenterUserId);

            // if the users have not been assigned to any, then continue the updates
            if (stationUserOk && districtUserOk) {

                // update the relevant old users
                if (oldStationUserId != null){
                    if (!oldStationUserId.equals(newStationUserId)) {
                        oldStationUserUpdateStatement.setNull(1, Types.INTEGER);
                        oldStationUserUpdateStatement.setInt(2,oldStationUserId);
                    }
                }
                if (oldDistrictCenterUserId != null){
                    if (!oldDistrictCenterUserId.equals(newDistrictCenterUserId)) {
                        oldDistrictUserUpdateStatement.setNull(1, Types.INTEGER);
                        oldDistrictUserUpdateStatement.setInt(2,oldDistrictCenterUserId);
                    }
                }

                // update relevant new users
                if (newStationUserId != null) {
                    newStationUserUpdateStatement.setInt(1, location.getId());
                    newStationUserUpdateStatement.setInt(2, newStationUserId);
                }
                if (newDistrictCenterUserId != null) {
                    newDistrictUserUpdateStatement.setInt(1, location.getId());
                    newDistrictUserUpdateStatement.setInt(2,newDistrictCenterUserId);
                }

                // update the location table
                if (location.getStationUserId() == null) locationUpdateStatement.setNull(1,  Types.INTEGER);
                else locationUpdateStatement.setInt(1,location.getStationUserId());
                if (location.getDistrictCenterUserId() == null) locationUpdateStatement.setNull(2,  Types.INTEGER);
                else locationUpdateStatement.setInt(2,location.getDistrictCenterUserId());
                locationUpdateStatement.setInt(3,location.getType());
                locationUpdateStatement.setInt(4,location.getId());

                // execute the queries
                if (oldStationUserId != null) if (!oldStationUserId.equals(newStationUserId)) oldStationUserUpdateStatement.executeUpdate();
                if (oldDistrictCenterUserId != null) if (!oldDistrictCenterUserId.equals(newDistrictCenterUserId)) oldDistrictUserUpdateStatement.executeUpdate();
                if (newStationUserId != null) newStationUserUpdateStatement.executeUpdate();
                if (newDistrictCenterUserId != null) newDistrictUserUpdateStatement.executeUpdate();
                result = locationUpdateStatement.executeUpdate();
            }

            if (!districtUserOk && !stationUserOk){
                result = 5;
            } else if (!districtUserOk) {
                result = 4;
            } else if (!stationUserOk) {
                result = 3;
            }
        }
        return result;
    }


    // delete location
    public boolean deleteLocation(int id) throws SQLException {
        boolean rowDeleted;
        try(
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_LOCATION_QUERY)
        ) {
            statement.setInt(1,id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

}
