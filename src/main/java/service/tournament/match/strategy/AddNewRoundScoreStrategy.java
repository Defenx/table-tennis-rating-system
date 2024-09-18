package service.tournament.match.strategy;

import dao.MatchDao;
import entity.Round;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import service.TransactionHandler;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class AddNewRoundScoreStrategy implements ScoreProcessingStrategy {

    private final MatchDao matchDao;
    private final TransactionHandler transactionHandler;

    @Override
    public void processScores(UUID matchId, HttpServletRequest req) {
        transactionHandler.executeWithTransaction(session -> {
            Integer score1 = Integer.parseInt(req.getParameter("score1"));
            Integer score2 = Integer.parseInt(req.getParameter("score2"));
            List<Round> rounds = matchDao.getRoundsByMatchId(matchId);
            int roundCount = rounds != null ? rounds.size() : 0;
            updateMatchScores(matchId, roundCount + 1, score1, score2, session);
        });
    }

    private void updateMatchScores(UUID matchId, Integer scoringRoundInMatch, Integer score1, Integer score2, Session session) {
        Round newRound = new Round();
        newRound.setRoundNumber(scoringRoundInMatch);
        newRound.setScore1(score1);
        newRound.setScore2(score2);
        newRound.setMatch(matchDao.getMatchById(matchId));
        session.persist(newRound);
    }
}