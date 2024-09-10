package servlet.tournament;

import constant.RequestAttributes;
import constant.RouteConstants;
import constant.RouteConstantsJSP;
import entity.Tournament;
import enums.ExtensionName;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(RouteConstants.TOURNAMENT_BY_ID)
public class RunningTournamentServlet extends BaseTournamentServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        UUID tournamentId = UUID.fromString(pathInfo.substring(1));
        Tournament tournament = tournamentService.getTournamentById(tournamentId);

        int trainingSets = extensionVariableTypeResolver.getTrainingSets(tournament);
        if (tournament.getStage() <= trainingSets) {
            req.setAttribute(RequestAttributes.TOURNAMENT, tournament);
            req.setAttribute(RequestAttributes.AVERAGE_RATING, tournament.getExtensions().stream()
                    .filter(extension -> extension.getName().equals(ExtensionName.AVERAGE_RATING))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Can`t get rating"))
                    .getValue()
            );
            req.getRequestDispatcher(RouteConstantsJSP.LAUNCHED_TOURNAMENT_JSP).forward(req, resp);
        } else
            resp.sendRedirect(RouteConstants.HOME); // заглушка
    }
}
