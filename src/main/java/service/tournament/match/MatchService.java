package service.tournament.match;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import service.TransactionHandler;
import service.tournament.match.strategy.AddNewRoundScoreStrategy;
import service.tournament.match.strategy.ScoreProcessingStrategy;
import service.tournament.match.strategy.UpdateExistingRoundScoreStrategy;
import service.tournament.round.RoundService;

import java.util.UUID;

@RequiredArgsConstructor
public class MatchService {
    private final RoundService roundService;

    public void processMatchScores(UUID matchId, HttpServletRequest req) {
        ScoreProcessingStrategy strategy;

        if (req.getParameter("score1") != null && req.getParameter("score2") != null) {
            strategy = new AddNewRoundScoreStrategy(roundService);
        } else {
            strategy = new UpdateExistingRoundScoreStrategy(roundService);
        }
        strategy.processScores(matchId, req);
    }
}
