package login.dao;

import user.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

import static connection.Connection.getConnection;

public class LoginDao {

    public static ArrayList<Integer> loginUser(User user) throws ClassNotFoundException {
        String GET_USER_QUERY = "SELECT password, level, user_id FROM users WHERE email=?";

        ArrayList<Integer> returnArr = new ArrayList<>();
        //      {login_status, user_level, user_id}
        //      0: logged in, 1: user doesn't exist, 2: incorrect password, 3: other error
        //      0: station, 1: district-center, 2: secretariat-office, 3: admin

        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_QUERY)
        ){
            preparedStatement.setString(1,user.getEmail());
            ResultSet rs = preparedStatement.executeQuery();

            int size = 0;
            while (rs.next()) {
                size++;
                String password = rs.getString("password");
                if (Objects.equals(password, user.getPassword())) {
                    returnArr.add(0);
                    int level = rs.getInt("level");
                    returnArr.add(level);
                    int id = rs.getInt("user_id");
                    returnArr.add(id);
                } else {
                    returnArr.add(2);
                }
            }
            if (size == 0) returnArr.add(1);

        } catch (SQLException e) {
            returnArr.add(3);
            e.printStackTrace();
        }

        return returnArr;
    }
}
