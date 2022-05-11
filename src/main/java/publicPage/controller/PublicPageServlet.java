package publicPage.controller;

import constants.Routes;
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

    public PublicPageServlet() {this.partyDao = new PartyDao();}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Party> parties = partyDao.selectAllParties();
        request.setAttribute("parties", parties);
        System.out.println(parties);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
        dispatcher.forward(request, response);
    }
}
