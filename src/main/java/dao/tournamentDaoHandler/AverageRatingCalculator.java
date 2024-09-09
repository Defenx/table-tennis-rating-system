package dao.tournamentDaoHandler;

import entity.TournamentParticipant;

import java.util.List;
import java.util.Objects;

public class AverageRatingCalculator {

    public static double calculate(List<TournamentParticipant> participants){
        return participants.stream()
                .map(p -> p.getUser().getRating())
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .average()
                .orElseThrow(() -> new RuntimeException("Unable to calculate average rating"));
    }
}
