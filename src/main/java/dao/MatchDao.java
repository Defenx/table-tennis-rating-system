package dao;

import entity.Match;
import entity.Round;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class MatchDao {
    private final SessionFactory sessionFactory;
    public Match getMatchById(UUID matchId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Match.class, matchId);
        }
    }

    public List<Round> getRoundsByMatchId(UUID matchId) {
        Match match = getMatchById(matchId);
        if (match != null) {
            return match.getRounds();
        }
        return null;
    }

    public Round getRoundByNumberInMatch(UUID matchId, Integer roundNumber) {
        List<Round> rounds = getRoundsByMatchId(matchId);
        if (rounds != null) {
            return rounds.stream()
                    .filter(round -> round.getRoundNumber().equals(roundNumber))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
