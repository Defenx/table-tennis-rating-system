package service.tournament.match;

import dao.MatchDao;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import service.TransactionHandler;
import service.tournament.match.strategy.AddNewRoundScoreStrategy;
import service.tournament.match.strategy.ScoreProcessingStrategy;
import service.tournament.match.strategy.UpdateExistingRoundScoreStrategy;

import java.util.UUID;

@RequiredArgsConstructor
public class MatchService {

    private final MatchDao matchDao;
    private final TransactionHandler transactionHandler;


    public void processMatchScores(UUID matchId, HttpServletRequest req) {
        ScoreProcessingStrategy strategy;
        if (req.getParameter("score1") != null && req.getParameter("score2") != null) {
            strategy = new AddNewRoundScoreStrategy(matchDao, transactionHandler);
        } else {
            strategy = new UpdateExistingRoundScoreStrategy(matchDao, transactionHandler);
        }
        strategy.processScores(matchId, req);
    }
}