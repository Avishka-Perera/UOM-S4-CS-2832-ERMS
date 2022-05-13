package party.controller;

import constants.Routes;
import constants.UserLevels;
import location.model.Location;
import party.dao.PartyDao;
import party.model.Party;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

import static utilities.Utilities.objFromRequest;
import static utilities.Utilities.requestJSONToString;

@WebServlet(name = "PartyServlet", value = Routes.ENDPOINT_PARTIES)
public class PartyServlet extends HttpServlet {

    private final PartyDao dao;

    public PartyServlet() { this.dao = new PartyDao();}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if ((int) session.getAttribute("level") == UserLevels.ADMIN_USER_LEVEL) {
            String name = request.getParameter("partyName");
            Party party = new Party(name);
            int[] results = new int[0];
            try {
                results = dao.addParty(party);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            response.setContentType("application/json");
            response.getWriter().write("{\"result\":"+results[0]+", \"id\":"+results[1]+"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if ((int) session.getAttribute("level") == UserLevels.ADMIN_USER_LEVEL){
            String jsonString = requestJSONToString(request);
            int partyId = Integer.parseInt(jsonString);

            boolean deleteStatus = false;
            try {
                deleteStatus = dao.deleteParty(partyId);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            response.setContentType("application/json");
            response.getWriter().write("{\"status\":"+deleteStatus+"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if ((int) session.getAttribute("level") == UserLevels.ADMIN_USER_LEVEL) {
            Party party = objFromRequest(request, Party.class);
            int result = dao.updatePartyName(party);

            response.setContentType("application/json");
            response.getWriter().write("{\"status\":"+result+"}");
        }
    }
}
