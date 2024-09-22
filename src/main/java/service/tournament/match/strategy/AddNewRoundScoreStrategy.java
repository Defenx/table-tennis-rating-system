package service.tournament.match.strategy;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import service.tournament.round.RoundService;

import java.util.UUID;

@RequiredArgsConstructor
public class AddNewRoundScoreStrategy implements ScoreProcessingStrategy {
    private final RoundService roundService;

    @Override
    public void processScores(UUID matchId, HttpServletRequest req) {
        Integer score1 = Integer.parseInt(req.getParameter("score1"));
        Integer score2 = Integer.parseInt(req.getParameter("score2"));
        roundService.createNewRound(matchId, score1, score2);
    }
}
