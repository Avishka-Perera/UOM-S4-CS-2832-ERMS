package api.votes;

import votes.dao.VotesDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static constants.Routes.ENDPOINT_API_VOTES;

@WebServlet(name = "VotesAPIServlet", value = ENDPOINT_API_VOTES)
public class VotesAPIServlet extends HttpServlet {
    private final VotesDao votesDao;
    public VotesAPIServlet() {
        votesDao = new VotesDao();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = votesDao.getJSONOfVotes();
        response.setContentType("application/json");
        response.getWriter().write("{\"votes\":"+json+"}");
    }
}
