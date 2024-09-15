package servlet.tournament;

import constant.RouteConstants;
import constant.SessionAttributes;
import entity.Tournament;
import entity.User;
import enums.Route;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(RouteConstants.TOURNAMENTS)
public class TournamentsServlet extends BaseTournamentServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Object[]> tournamentsList = tournamentService.getProcessingTournaments();
        req.setAttribute("tournamentsList",tournamentsList);

        tournamentsList.stream().forEach(System.out::println);

        req.getRequestDispatcher(Route.TOURNAMENTS.getJspPath()).forward(req, resp);
    }
}
