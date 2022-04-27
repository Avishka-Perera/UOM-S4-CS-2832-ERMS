package user.dao;

import constants.JDBC;
import user.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private String jdbcURL = JDBC.JDBC_URL;
    private String jdbcUsername = JDBC.JDBC_USERNAME;
    private String jdbcPassword = JDBC.JDBC_PASSWORD;

    private static final String INSERT_USERS_QUERY = "INSERT INTO users (name, email, password, contact_number, level) VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id=?;";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users;";
    private static final String DELETE_USER_QUERY = "DELETE FROM USERS WHERE id=?;";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET name=?, email=?, password=?, contact_number=?, level=? WHERE id=?;";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // create new user
    public int addUser(User user) throws ClassNotFoundException {
        int result = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(this.INSERT_USERS_QUERY)){
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getContactNumber());
            preparedStatement.setInt(5,user.getUserLevel());

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // update user
    public boolean updateUser(User user) throws SQLException {
        boolean result;
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY);
                ) {

            statement.setString(1,user.getName());
            statement.setString(2,user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getContactNumber());
            statement.setInt(5, user.getUserLevel());
            statement.setInt(6, user.getId());

            result = statement.executeUpdate() > 0;
        }
        return result;
    }

    // select user by id
    public User selectUser(int id) {
        User user = null;
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID);
                ) {
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                //name=?, email=?, password=?, contact_number=?, level=?
                String name = rs.getString("name");
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

    // select users
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS);
        ) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                //name=?, email=?, password=?, contact_number=?, level=?
                int id = rs.getInt("id");
                String name = rs.getString("name");
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

    // delete user
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try(
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_USER_QUERY);
                ) {
            statement.setInt(1,id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
}
