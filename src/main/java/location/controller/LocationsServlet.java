package location.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import constants.Routes;
import constants.UserLevels;
import location.dao.LocationDao;
import location.model.Location;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
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

            int locationId = data.getLocationId();
            Integer stationUserId = data.getStationUserId();
            Integer districtUserId = data.getDistrictUserId();
            int type = data.getType();

            Location location = new Location(locationId, stationUserId, districtUserId, type);
            boolean result = false;
            try {
                result = dao.updateLocation(location);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            response.setContentType("application/json");
            response.getWriter().write("{\"status\":"+result+"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if ((int) session.getAttribute("level") == UserLevels.ADMIN_USER_LEVEL){
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
    private int locationId;
    private Integer stationUserId;
    private Integer districtUserId;
    private int type;

    public PutRequestData() {}

    public int getLocationId() {
        return locationId;
    }

    public Integer getStationUserId() {
        return stationUserId;
    }

    public Integer getDistrictUserId() {
        return districtUserId;
    }

    public int getType() {
        return type;
    }

    public String toString() {
        return "PutRequestData [ locationId: "+locationId+", stationUserId: "+stationUserId+", districtUserId: "+districtUserId+", type: "+type+ " ]";
    }
}
