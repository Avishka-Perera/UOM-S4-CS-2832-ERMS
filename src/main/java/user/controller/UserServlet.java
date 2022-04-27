package user.controller;

import user.dao.UserDao;
import user.model.User;
import constants.Routes;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddUserServlet", value = Routes.ROUTE_ADD_USER)
public class UserServlet extends HttpServlet {

    private UserDao dao;

    public UserServlet() {
        this.dao = new UserDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

            response.sendRedirect(request.getContextPath() + Routes.ROUTE_USER_MANAGEMENT);
        }
    }
}
