package manageVote.controller;

import constants.Routes;
import constants.UserLevels;
import location.dao.LocationDao;
import location.model.Location;
import party.dao.PartyDao;
import party.model.Party;
import user.dao.UserDao;
import user.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManageVoteServlet", value = "/"+Routes.ROUTE_VOTE_MANAGEMENT)
public class ManageVoteServlet extends HttpServlet {

    private final LocationDao locationDao;
    private final UserDao userDao;
    private final PartyDao partyDao;

    public ManageVoteServlet() {this.locationDao = new LocationDao(); this.userDao = new UserDao(); this.partyDao = new PartyDao();}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if ((int) session.getAttribute("level") == UserLevels.ADMIN_USER_LEVEL) {

            List<User> stationUsers = userDao.selectStationUsers();
            request.setAttribute("stationUsers", stationUsers);

            List<User> districtUsers = userDao.selectDistrictUsers();
            request.setAttribute("districtUsers", districtUsers);

            List<Location> locations = locationDao.selectAllLocations();
            request.setAttribute("locations", locations);

            List<Party> parties = partyDao.selectAllParties();
            request.setAttribute("parties", parties);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/voteManagement.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/" + Routes.ROUTE_LOGIN);
        }
    }

}
