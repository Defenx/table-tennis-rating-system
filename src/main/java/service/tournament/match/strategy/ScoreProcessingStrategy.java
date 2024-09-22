package service.tournament.match.strategy;

import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

public interface ScoreProcessingStrategy {
    void processScores(UUID matchId, HttpServletRequest req);
}
