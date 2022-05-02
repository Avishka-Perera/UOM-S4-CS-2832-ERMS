//  station - 0
//  district-center - 1
//  secretariat-office - 2
//  admin - 3

package login.controller;

import constants.Routes;
import constants.UserLevels;
import login.dao.LoginDao;
import login.model.LoginModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@WebServlet(name = "LoginServlet", value = Routes.ENDPOINT_LOGIN)
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
        dispatcher.forward(request, response);

        String logout = request.getParameter("logout");
        if (Objects.equals(logout, "1")) {
            HttpSession session = request.getSession();
            session.invalidate();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        LoginModel loginModel = new LoginModel(email, password);

        ArrayList<Integer> result = new ArrayList<>();
        //      {login_status, user_level, user_id}
        //      login_status --> 0: logged in, 1: user doesn't exist, 2: incorrect password, 3: other error
        //      user_level --> 0: station, 1: district-center, 2: secretariat-office, 3: admin

        try {
            result = LoginDao.loginUser(loginModel, session, request, response);
            if (result.get(0)==0) {
                int level = result.get(1);
                int id = result.get(2);
                session.setAttribute("level", level);
                session.setAttribute("id", id);
                if (level == UserLevels.ADMIN_USER_LEVEL) {
                    response.sendRedirect(Routes.ROUTE_VOTE_MANAGEMENT);
                } else  {
                    response.sendRedirect(Routes.ROUTE_VOTES);
                }
            }  else {
                response.sendRedirect( Routes.ROUTE_LOGIN + "?error="+result.get(0));
            }
        } catch (ClassNotFoundException e) {
            result.add(0);
            e.printStackTrace();
        }

        System.out.println(result);
    }
}
