package servlet.home;

import constant.RouteConstants;
import entity.Tournament;
import enums.Route;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(RouteConstants.DELETE_TOURNAMENT_BY_ID)
public class HomeServletDeleteTournamentById extends BaseHomeServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        UUID userId = UUID.fromString(pathInfo.substring(1));

        Tournament tournament = tournamentService.getTournamentById(userId);
        tournamentService.deleteTournament(tournament);

        resp.sendRedirect(Route.HOME.getPath());
    }
}
