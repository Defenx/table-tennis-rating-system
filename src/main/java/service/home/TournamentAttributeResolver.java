package service.home;

import constant.RequestAttributes;
import entity.Tournament;
import entity.TournamentParticipant;
import entity.User;
import lombok.RequiredArgsConstructor;
import service.tournament.TournamentService;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class TournamentAttributeResolver {
    private final TournamentService tournamentService;

    public Map<String, Object> findNeededAttributes(User user, List<Tournament> tournaments) {
        Map<String, Object> attributes = new HashMap<>();
        for (Tournament tournament : tournaments) {
            tournament.getParticipants().sort(Comparator.comparing((TournamentParticipant tp) -> tp.getUser().getRating()).reversed());
            attributes.put(RequestAttributes.IS_CURRENT_USER_REGISTERED_FOR_TOURNAMENT_ATTRIBUTE + tournament.getId(),
                    tournamentService.isAlreadyParticipated(user, tournament));
        }
        attributes.put(RequestAttributes.TOURNAMENTS_WITH_STATUS_NEW,tournaments);
        return attributes;
    }
}
