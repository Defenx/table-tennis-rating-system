package service.home;

import constant.RequestAttributes;
import entity.TournamentParticipant;
import entity.User;
import lombok.RequiredArgsConstructor;
import service.TournamentService;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

@RequiredArgsConstructor
public class TournamentAttributeResolver {
    private final TournamentService tournamentService;

    public Map<String, Object> findNeededAttributes(User user) {
        var tournamentOptional = tournamentService.getNewTournament();
        return tournamentOptional.map(tournament -> {
            tournament.getParticipants().sort(Comparator.comparing((TournamentParticipant tp) -> tp.getUser().getRating()).reversed());

            return Map.ofEntries(
                    Map.entry(RequestAttributes.TOURNAMENT_REQUEST_ATTRIBUTE, tournament),
                    Map.entry(RequestAttributes.IS_CURRENT_USER_REGISTERED_FOR_TOURNAMENT_SESSION_ATTRIBUTE, tournamentService.isAlreadyParticipated(user, tournament)),
                    Map.entry(RequestAttributes.IS_SOMEONE_REGISTERED_FOR_TOURNAMENT_SESSION_ATTRIBUTE, !tournament.getParticipants().isEmpty())
            );
        }).orElseGet(Collections::emptyMap);
    }
}
