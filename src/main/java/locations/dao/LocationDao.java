package locations.dao;
import locations.model.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static connection.Connection.getConnection;

public class LocationDao {

    private static final String INSERT_LOCATION_QUERY = "INSERT INTO locations (name, type) VALUES (?, ?);";
    private static final String SELECT_ALL_LOCATIONS_QUERY = "SELECT * FROM locations;";
    private static final String SELECT_LOCATION_BY_ID_QUERY = "SELECT * FROM locations WHERE id=?;";
    private static final String SELECT_LOCATION_BY_NAME_QUERY = "SELECT * FROM locations WHERE name=? LIMIT 1;";
    private static final String SELECT_LAST_RECORD_QUERY = "SELECT * FROM locations ORDER BY ID DESC LIMIT 1";
    private static final String DELETE_LOCATION_QUERY = "DELETE FROM locations WHERE id=?;";
    private static final String UPDATE_LOCATION_QUERY = "UPDATE locations SET station_user_id=?, district_center_user_id=?, type=? WHERE id=?;";

    // select all locations
    public List<Location> selectAllLocations() {
        List<Location> locations = new ArrayList<>();
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_LOCATIONS_QUERY)
        ) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Integer stationUserId = (Integer) rs.getObject("station_user_id");
                Integer districtCenterUserId = (Integer) rs.getObject("district_center_user_id");
                int type = rs.getInt("type");

                locations.add(new Location(id, name, stationUserId, districtCenterUserId, type));
            }
        } catch (SQLException e) {
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
                String name = rs.getString("name");
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
                    id = rs2.getInt("id");
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
    public boolean updateLocation(Location location) throws SQLException {
        boolean result;
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_LOCATION_QUERY)
        ) {

            if (location.getStationUserId() == null) statement.setNull(1,  Types.INTEGER);
            else statement.setInt(1,location.getStationUserId());
            if (location.getDistrictCenterUserId() == null) statement.setNull(2,  Types.INTEGER);
            else statement.setInt(2,location.getDistrictCenterUserId());
            statement.setInt(3,location.getType());
            statement.setInt(4,location.getId());

            result = statement.executeUpdate() > 0;
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
