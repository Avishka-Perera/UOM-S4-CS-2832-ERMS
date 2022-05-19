package user.controller;

import constants.RequestTasks;
import constants.UserLevels;
import constants.Routes;
import gsonClasses.UserPutRequestData;
import user.dao.UserDao;
import user.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static utilities.Utilities.objFromRequest;
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

        if (session.getAttribute("level") != null) {
            if ((int) session.getAttribute("level") == UserLevels.ADMIN_USER_LEVEL) {
                List<User> users = dao.selectAllUsers();
                request.setAttribute("users", users);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(Routes.ROUTE_LOGIN);
            }
        } else {
            response.sendRedirect(Routes.ROUTE_LOGIN);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if ((int) session.getAttribute("level") == UserLevels.ADMIN_USER_LEVEL){
            User user = objFromRequest(request, User.class);

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
            UserPutRequestData requestData = objFromRequest(request, UserPutRequestData.class);
            int task = requestData.getTask();
            User user = requestData.getUser();
            int result = 0;
            try {
                if (task == RequestTasks.User.Put.updateUserDetails) result = dao.updateUserDetails(user);
                if (task == RequestTasks.User.Put.updateUserLevel) result = dao.updateUserLevel(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            response.setContentType("application/json");
            response.getWriter().write("{\"status\":"+result+"}");
        }
    }
}

