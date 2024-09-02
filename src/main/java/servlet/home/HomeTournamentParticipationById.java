package servlet.home;

import constant.RouteConstants;
import constant.SessionAttributes;
import entity.Tournament;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.home.TournamentServiceMethod;

import java.io.IOException;
import java.util.UUID;

@WebServlet(RouteConstants.PARTICIPATION_BY_TOURNAMENT_ID)
public class HomeTournamentParticipationById extends BaseHomeServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getAndApplyParametersToMethod(req, tournamentService::participate);

        resp.sendRedirect(RouteConstants.HOME);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getAndApplyParametersToMethod(req,tournamentService::withdrawFromTheTournament);

        resp.sendRedirect(RouteConstants.HOME);
    }

    private void getAndApplyParametersToMethod(HttpServletRequest req, TournamentServiceMethod method) {
        User user = (User) req.getSession().getAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE);
        String pathInfo = req.getPathInfo();
        UUID tournamentId = UUID.fromString(pathInfo.substring(1));

        Tournament tournament = tournamentService.getTournamentById(tournamentId);

        method.apply(user, tournament);
    }
}
