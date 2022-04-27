package locations.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import constants.Routes;
import constants.UserLevels;
import locations.dao.LocationDao;
import locations.model.Location;
import user.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import static utilities.Utilities.requestJSONToString;

@WebServlet(name = "LocationsServlet", value = "/"+ Routes.ROUTE_LOCATIONS)
public class LocationsServlet extends HttpServlet {

    private final LocationDao dao;

    public LocationsServlet() {this.dao = new LocationDao();}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        System.out.println((int) session.getAttribute("level"));
        if ((int) session.getAttribute("level") == UserLevels.ADMIN_USER_LEVEL) {
            String name = request.getParameter("name");
            String type = request.getParameter("type");
            Location location = new Location(name, Integer.parseInt(type));
            int[] results = new int[0];
            try {
                results = dao.addLocation(location);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            response.setContentType("application/json");
            response.getWriter().write("{\"result\":"+results[0]+", \"id\":"+results[1]+"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if ((int) session.getAttribute("level") == UserLevels.ADMIN_USER_LEVEL){
            // read the data of the request
            String str = requestJSONToString(request);
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            PutRequestData data = gson.fromJson(str, PutRequestData.class);
//            int userId = data.getUserId();
//            int userLevel = data.getUserLevel();
//            System.out.println(userId + " " + userLevel);
//            User user = new User(userId, userLevel);
//            boolean result = false;
//            try {
//                result = dao.updateUser(user);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }

//            response.setContentType("application/json");
//            response.getWriter().write("{\"status\":"+result+"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if ((int) session.getAttribute("level") == UserLevels.ADMIN_USER_LEVEL){
            System.out.println("Got the request");
            String jsonString = requestJSONToString(request);
            int locationId = Integer.parseInt(jsonString);

            boolean deleteStatus = false;
            try {
                deleteStatus = dao.deleteLocation(locationId);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            response.setContentType("application/json");
            response.getWriter().write("{\"status\":"+deleteStatus+"}");
        }

    }
}

class PutRequestData {

}
