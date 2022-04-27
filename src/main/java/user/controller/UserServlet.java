package user.controller;

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
import java.util.Objects;

@WebServlet(name = "AddUserServlet", value = Routes.ROUTE_USERS)
public class UserServlet extends HttpServlet {

    private final UserDao dao;

    public UserServlet() {
        this.dao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String level = String.valueOf(session.getAttribute("level"));
        if (Objects.equals(level, "3")) {
            List<User> users = dao.selectAllUsers();
            request.setAttribute("users", users);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + Routes.ROUTE_LOGIN);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if ((int) session.getAttribute("level") == 3){
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String contactNumber = request.getParameter("contactNumber");
            int userLevel = Integer.parseInt(request.getParameter("userLevel"));

            User user = new User(name, email, password, contactNumber, userLevel);
            int result = 0;
            try {
                result = dao.addUser(user);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

//            response.addHeader("", String.valueOf(result));
            System.out.println(result);
            response.sendRedirect(request.getContextPath() + Routes.ROUTE_USERS);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str;
        while( (str = br.readLine()) != null ){
            sb.append(str);
        }
        int userId = Integer.parseInt(sb.toString());

        boolean deleteStatus = false;
        try {
            deleteStatus = dao.deleteUser(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(userId);

        response.setContentType("application/json");
        response.getWriter().write("{\"status\":"+deleteStatus+"}");
    }
}
