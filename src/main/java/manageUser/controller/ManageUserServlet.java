package manageUser.controller;

import constants.Routes;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "ManageUserServlet", value = Routes.ROUTE_USER_MANAGEMENT)
public class ManageUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String level = String.valueOf(session.getAttribute("level"));
        if (Objects.equals(level, "3")) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/manageUser.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + Routes.ROUTE_LOGIN);
        }
    }
}
