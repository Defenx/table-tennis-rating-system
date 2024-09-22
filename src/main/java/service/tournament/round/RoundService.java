package service.tournament.round;

import dao.TournamentDao;
import entity.Match;
import entity.Round;
import lombok.RequiredArgsConstructor;
import service.TransactionHandler;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class RoundService {
    private final TransactionHandler transactionHandler;
    private final TournamentDao tournamentDao;
    public void updateRoundScores(UUID matchId, Integer roundNumber, Integer score1, Integer score2) {
        transactionHandler.executeWithTransaction(session -> {
            List<Round> rounds = tournamentDao.getMatchById(matchId) != null ? tournamentDao.getMatchById(matchId).getRounds() : List.of();
            Optional<Round> roundOpt = rounds.stream()
                    .filter(round -> round.getRoundNumber().equals(roundNumber))
                    .findFirst();
            if (roundOpt.isPresent()) {
                Round round = roundOpt.get();
                round.setScore1(score1);
                round.setScore2(score2);
                session.merge(round);
            }
        });
    }

    public void createNewRound(UUID matchId, Integer score1, Integer score2) {
        transactionHandler.executeWithTransaction(session -> {
            Match match = tournamentDao.getMatchById(matchId);
            int roundCount = match.getRounds() != null ? match.getRounds().size() : 0;
            Round newRound = Round.builder()
                    .roundNumber(roundCount + 1)
                    .score1(score1)
                    .score2(score2)
                    .match(match)
                    .build();
            session.persist(newRound);
        });
    }
}
