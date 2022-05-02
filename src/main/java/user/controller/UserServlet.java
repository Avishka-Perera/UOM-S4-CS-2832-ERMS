package user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import constants.UserLevels;
import gsonClasses.UserPutRequestData;
import user.dao.UserDao;
import user.model.User;
import constants.Routes;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static utilities.Utilities.requestJSONToString;

@WebServlet(name = "UserServlet", value = Routes.ENDPOINT_USERS)
public class UserServlet extends HttpServlet {

    private final UserDao dao;

    public UserServlet() {
        this.dao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if ((int) session.getAttribute("level") == UserLevels.ADMIN_USER_LEVEL) {
            List<User> users = dao.selectAllUsers();
            request.setAttribute("users", users);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/" + Routes.ROUTE_LOGIN);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if ((int) session.getAttribute("level") == UserLevels.ADMIN_USER_LEVEL){
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String contactNumber = request.getParameter("contactNumber");
            int userLevel = Integer.parseInt(request.getParameter("userLevel"));

            User user = new User(name, email, password, contactNumber, userLevel);
            try {
                int[] results = dao.addUser(user);
                response.setContentType("application/json");
                response.getWriter().write("{\"result\":"+results[0]+", \"id\":"+results[1]+"}");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                response.setContentType("application/json");
                response.getWriter().write("{\"result\":"+0+", \"id\":"+0+"}");
            }

        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if ((int) session.getAttribute("level") == UserLevels.ADMIN_USER_LEVEL){
            String jsonString = requestJSONToString(request);
            int userId = Integer.parseInt(jsonString);

            boolean deleteStatus = false;
            try {
                deleteStatus = dao.deleteUser(userId);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            response.setContentType("application/json");
            response.getWriter().write("{\"status\":"+deleteStatus+"}");
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if ((int) session.getAttribute("level") == UserLevels.ADMIN_USER_LEVEL){
            // read the data of the request
            StringBuilder sb = new StringBuilder();
            BufferedReader br = request.getReader();
            String str;
            while( (str = br.readLine()) != null ) sb.append(str);
            str = sb.toString();
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            UserPutRequestData data = gson.fromJson(str, UserPutRequestData.class);
            int userId = data.getUserId();
            int userLevel = data.getUserLevel();
            User user = new User(userId, userLevel);
            boolean result = false;
            try {
                result = dao.updateUser(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            response.setContentType("application/json");
            response.getWriter().write("{\"status\":"+result+"}");
        }
    }
}

