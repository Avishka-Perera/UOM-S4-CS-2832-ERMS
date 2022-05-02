package location.controller;

import constants.Routes;
import constants.UserLevels;
import gsonClasses.LocationPutRequestData;
import location.dao.LocationDao;
import location.model.Location;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

import static utilities.Utilities.*;

@WebServlet(name = "LocationsServlet", value = Routes.ENDPOINT_LOCATIONS)
public class LocationsServlet extends HttpServlet {

    private final LocationDao dao;

    public LocationsServlet() {this.dao = new LocationDao();}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        Object levelObj = session.getAttribute("level");
        if (levelObj != null) {
            int level = (int) levelObj;
            if (level == UserLevels.ADMIN_USER_LEVEL) {
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
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Object levelObj = session.getAttribute("level");
        if (levelObj != null) {
            int level = (int) levelObj;
            if (level == UserLevels.ADMIN_USER_LEVEL){
                // read the data of the request
                LocationPutRequestData data = objFromRequest(request, LocationPutRequestData.class);

                int locationId = data.getLocationId();
                Integer stationUserId = data.getStationUserId();
                Integer districtUserId = data.getDistrictUserId();
                int type = data.getType();

                Location location = new Location(locationId, stationUserId, districtUserId, type);
                int result = 0;
                try {
                    result = dao.updateLocation(location);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                response.setContentType("application/json");
                response.getWriter().write("{\"status\":"+result+"}");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();Object levelObj = session.getAttribute("level");
        if (levelObj != null) {
            int level = (int) levelObj;
            if (level == UserLevels.ADMIN_USER_LEVEL){
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
}

