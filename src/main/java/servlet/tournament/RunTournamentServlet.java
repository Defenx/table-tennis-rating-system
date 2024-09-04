package servlet.tournament;

import constant.RouteConstants;
import constant.SessionAttributes;
import entity.Tournament;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@WebServlet(RouteConstants.RUN_TOURNAMENT_BY_ID)
public class RunTournamentServlet extends BaseTournamentServlet {
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
                .orElse(Double.NaN);
        if (tournament.getStage() < 4) {//tournament.getExtensions().size()){ // заглушка для разработки
            req.setAttribute("stage", tournament.getStage());
            req.setAttribute("avg", avg);
            req.setAttribute("matches", tournament.getMatches());
            req.getRequestDispatcher("/tournament.jsp").forward(req, resp);
        } else
            resp.sendRedirect(RouteConstants.HOME); // заглушка
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE);
        String pathInfo = req.getPathInfo();

        System.out.println("Request uri: " + req.getRequestURI());

        UUID tournamentId = UUID.fromString(pathInfo.substring(1));
        Tournament tournament = tournamentService.getTournamentById(tournamentId);
        Optional<User> optionalMagicUser = tournamentService.runTournament(tournament);

        if (optionalMagicUser.isPresent()) {
            User magicUser = optionalMagicUser.get();
            if (magicUser.getId().equals(user.getId())) {
                resp.sendRedirect(RouteConstants.HOME); // Заглушка
            }
        }

        resp.sendRedirect(req.getRequestURI());
    }
}
