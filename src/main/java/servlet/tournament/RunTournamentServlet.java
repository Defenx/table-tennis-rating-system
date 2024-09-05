package servlet.tournament;

import constant.RouteConstants;
import entity.Tournament;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(RouteConstants.RUN_TOURNAMENT_BY_ID)
public class RunTournamentServlet extends BaseTournamentServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        UUID tournamentId = UUID.fromString(pathInfo.substring(1));
        Tournament tournament = tournamentService.getTournamentById(tournamentId);
        tournamentService.runTournament(tournament);

        resp.sendRedirect(RouteConstants.LAUNCHED_TOURNAMENTS);
    }
}
