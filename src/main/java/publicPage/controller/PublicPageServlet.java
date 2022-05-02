package publicPage.controller;

import constants.Routes;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "PublicPageServlet", value = Routes.ENDPOINT_PUBLIC)
public class PublicPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Got request");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
        System.out.println("Got the dispatcher");
        dispatcher.forward(request, response);
        System.out.println("Dispatched the request");
    }
}
