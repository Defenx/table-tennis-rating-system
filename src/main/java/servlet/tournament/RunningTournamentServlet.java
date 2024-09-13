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
import java.math.BigDecimal;
import java.util.UUID;

@WebServlet(RouteConstants.TOURNAMENT_BY_ID)
public class RunningTournamentServlet extends BaseTournamentServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        UUID tournamentId = UUID.fromString(pathInfo.substring(1));
        Tournament tournament = tournamentService.getTournamentById(tournamentId);

        int trainingSets = extensionVariableTypeResolver.getExtensionValue(ExtensionName.VICTORIES_IN_TRAINING_MATCHES, tournament);
        BigDecimal averageRating = extensionVariableTypeResolver.getExtensionValue(ExtensionName.AVERAGE_RATING, tournament);

        if (tournament.getStage() <= trainingSets) {
            req.setAttribute(RequestAttributes.TOURNAMENT, tournament);
            req.setAttribute(RequestAttributes.AVERAGE_RATING, averageRating);
            req.getRequestDispatcher(RouteConstantsJSP.LAUNCHED_TOURNAMENT_JSP).forward(req, resp);
        } else
            resp.sendRedirect(RouteConstants.HOME); // заглушка
    }
}
