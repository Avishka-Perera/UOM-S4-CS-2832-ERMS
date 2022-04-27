package login.dao;

import login.model.LoginModel;
import constants.Routes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class LoginDao {

    public static ArrayList<Integer> loginUser(LoginModel loginModel, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException {
        String GET_USER_QUERY = "SELECT password, level FROM users WHERE email='"+loginModel.getEmail()+"'";

        ArrayList<Integer> returnArr = new ArrayList<>();
        //      {login_status, user_level}
        //      0: logged in, 1: user doesn't exist, 2: incorrect password, 3: other error
        //      0: station, 1: district-center, 2: secretariat-office, 3: admin

        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/erms?useSSL=false","root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_QUERY)){

            ResultSet rs = preparedStatement.executeQuery();

            int size = 0;
            while (rs.next()) {
                size++;
                String password = rs.getString("password");
                if (Objects.equals(password, loginModel.getPassword())) {
                    returnArr.add(0);
                    int level = rs.getInt("level");
                    returnArr.add(level);
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
