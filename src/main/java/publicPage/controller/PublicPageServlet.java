package publicPage.controller;

import constants.Routes;
import location.dao.LocationDao;
import location.model.Location;
import party.dao.PartyDao;
import party.model.Party;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PublicPageServlet", value = Routes.ENDPOINT_PUBLIC)
public class PublicPageServlet extends HttpServlet {
    private final PartyDao partyDao;
    private final LocationDao locationDao;

    public PublicPageServlet() {
        this.partyDao = new PartyDao();
        this.locationDao = new LocationDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Party> parties = partyDao.getAllParties();
        request.setAttribute("parties", parties);

        List<Location> locations = locationDao.getAllLocationNames();
        request.setAttribute("locations", locations);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
        dispatcher.forward(request, response);
    }
}
