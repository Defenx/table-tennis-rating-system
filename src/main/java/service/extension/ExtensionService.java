package service.extension;

import entity.TournamentParticipant;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ExtensionService {

    public BigDecimal calculateAverageRating(List<TournamentParticipant> participants) {
        var average = participants.stream()
                .map(participant -> participant.getUser().getRating())
                .reduce(BigDecimal::add)
                .map(sum -> sum.divide(BigDecimal.valueOf(participants.size()), RoundingMode.HALF_EVEN));

        return average.orElse(BigDecimal.ZERO);
    }
}
