package service.home;

import entity.TournamentParticipant;
import entity.User;
import lombok.RequiredArgsConstructor;
import service.TournamentService;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

@RequiredArgsConstructor
public class TournamentAttributeResolver {

    public static final String IS_SOMEONE_REGISTERED_FOR_TOURNAMENT_SESSION_ATTRIBUTE = "isSomeoneRegisteredForTournament";
    public static final String IS_CURRENT_USER_REGISTERED_FOR_TOURNAMENT_SESSION_ATTRIBUTE = "isCurrentUserRegisteredForTournament";
    public static final String TOURNAMENT_REQUEST_ATTRIBUTE = "tournament";
    private static final String USERS_COUNT = "usersCount";

    private final TournamentService tournamentService;

    public Map<String, Object> findNeededAttributes(User user) {
        var tournamentOptional = tournamentService.getNewTournament();
        return tournamentOptional.map(tournament -> {
            tournament.getParticipants().sort(Comparator.comparing((TournamentParticipant tp) -> tp.getUser().getRating()).reversed());

            return Map.ofEntries(
                    Map.entry(TOURNAMENT_REQUEST_ATTRIBUTE, tournament),
                    Map.entry(IS_CURRENT_USER_REGISTERED_FOR_TOURNAMENT_SESSION_ATTRIBUTE, tournamentService.isAlreadyParticipated(user, tournament)),
                    Map.entry(IS_SOMEONE_REGISTERED_FOR_TOURNAMENT_SESSION_ATTRIBUTE, !tournament.getParticipants().isEmpty()),
                    Map.entry(USERS_COUNT, tournamentService.getParticipantsListLength(tournament))
            );
        }).orElseGet(Collections::emptyMap);
    }
}
