package addLocation.controller;

import constants.Routes;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddLocationServlet", value = Routes.ROUTE_ADD_LOCATION)
public class AddLocationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
