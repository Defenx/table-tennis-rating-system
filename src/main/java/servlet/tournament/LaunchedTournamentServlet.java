package servlet.tournament;

import constant.RouteConstants;
import entity.Tournament;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@WebServlet(RouteConstants.TOURNAMENT_BY_ID)
public class LaunchedTournamentServlet extends BaseTournamentServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        UUID tournamentId = UUID.fromString(pathInfo.substring(1));
        Tournament tournament = tournamentService.getTournamentById(tournamentId);

        double avg = tournament.getParticipants().stream()
                .map(p -> p.getUser().getRating())
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        if (tournament.getStage() < 4) {//tournament.getExtensions().size()){ // заглушка для разработки
            req.setAttribute("stage", tournament.getStage());
            req.setAttribute("avg", avg);
            req.setAttribute("matches", tournament.getMatches());
            req.getRequestDispatcher("/launchedTournament.jsp").forward(req, resp);
        } else
            resp.sendRedirect(RouteConstants.HOME); // заглушка
    }
}
