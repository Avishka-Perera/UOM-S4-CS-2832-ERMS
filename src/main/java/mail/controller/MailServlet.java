package mail.controller;

import constants.Routes;
import constants.UserLevels;
import gsonClasses.PartyVote;
import gsonClasses.SendMailData;
import location.dao.LocationDao;
import location.model.Location;
import mail.functions.MailFunctions;
import party.dao.PartyDao;
import party.model.Party;
import votes.dao.VotesDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utilities.Utilities.objFromRequest;

@WebServlet(name = "MailServlet", value = Routes.ENDPOINT_SENDMAIL)
public class MailServlet extends HttpServlet {

    private VotesDao votesDao;
    private PartyDao partyDao;
    private LocationDao locationDao;

    public MailServlet() {
        votesDao = new VotesDao();
        partyDao = new PartyDao();
        locationDao = new LocationDao();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if ((int) session.getAttribute("level") == UserLevels.ADMIN_USER_LEVEL){

            SendMailData mailsObj = objFromRequest(request, SendMailData.class);

            String report = votesDao.getReport();

            int successCount = 0;
            int failCount = 0;
            for (String emailAddress :
                    mailsObj.getMails()) {
                if (MailFunctions.sendMail(emailAddress, report)) successCount++;
                else failCount++;
            }

            response.setContentType("application/json");
            response.getWriter().write("{\"successCount\":"+successCount+",\"failCount\":"+failCount+"}");
        }
    }
}
