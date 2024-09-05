package servlet.tournament;

import constant.RouteConstants;
import entity.Tournament;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(RouteConstants.LAUNCHED_TOURNAMENTS)
public class TournamentsServlet extends BaseTournamentServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Tournament> launchedTournaments = tournamentService.getLaunchedTournaments();

        req.setAttribute("launchedTournaments", launchedTournaments);
        req.getRequestDispatcher("/tournaments.jsp").forward(req, resp);
    }
}
