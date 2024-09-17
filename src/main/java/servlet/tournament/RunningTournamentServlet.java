package servlet.tournament;

import constant.RequestAttributes;
import constant.RouteConstants;
import constant.RouteConstantsJSP;
import entity.Match;
import entity.Tournament;
import enums.ExtensionName;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

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
        }
        else
            resp.sendRedirect(RouteConstants.HOME);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String matchId = req.getParameter("matchId");
        String userId1 = req.getParameter("userId1");
        String userId2 = req.getParameter("userId2");
        Integer score1 = Integer.parseInt(req.getParameter("score1"));
        Integer score2 = Integer.parseInt(req.getParameter("score2"));
        Integer scoringRoundInMatch = Integer.parseInt(req.getParameter("roundsInMatch"));
        UUID tournamentId = UUID.fromString(pathInfo.substring(1));
        if (matchId != null && userId1 != null && userId2 != null) {
            UUID matchUUID = UUID.fromString(matchId);
            tournamentService.updateMatchScores(matchUUID, scoringRoundInMatch, score1, score2);
            System.out.println(true);
        }
        resp.sendRedirect(RouteConstants.TOURNAMENT_BY_ID.replace("*", tournamentId.toString()));
    }
}
