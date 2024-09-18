package service.tournament.match.strategy;

import dao.MatchDao;
import entity.Round;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import service.TransactionHandler;

import java.util.Enumeration;
import java.util.UUID;

@RequiredArgsConstructor
public class UpdateExistingRoundScoreStrategy implements ScoreProcessingStrategy {

    private final MatchDao matchDao;
    private final TransactionHandler transactionHandler;

    @Override
    public void processScores(UUID matchId, HttpServletRequest req) {
        transactionHandler.executeWithTransaction(session -> {
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                if (paramName.startsWith("roundScore1_")) {
                    String roundIndexStr = paramName.substring("roundScore1_".length());
                    String roundScore1Param = req.getParameter("roundScore1_" + roundIndexStr);
                    String roundScore2Param = req.getParameter("roundScore2_" + roundIndexStr);
                    if (roundScore1Param != null && roundScore2Param != null) {
                        Integer roundScore1 = Integer.parseInt(roundScore1Param);
                        Integer roundScore2 = Integer.parseInt(roundScore2Param);
                        int roundIndex = Integer.parseInt(roundIndexStr);
                        updateMatchScores(matchId, roundIndex + 1, roundScore1, roundScore2, session);
                    }
                }
            }
        });
    }

    private void updateMatchScores(UUID matchId, Integer scoringRoundInMatch, Integer score1, Integer score2, Session session) {
        Round round = matchDao.getRoundByNumberInMatch(matchId, scoringRoundInMatch);
        if (round != null) {
            round.setScore1(score1);
            round.setScore2(score2);
            session.merge(round);
        }
    }
}