package service.home;

import entity.Tournament;
import entity.TournamentParticipant;
import entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import service.TournamentService;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class TournamentHelperService {
    public static final String IS_SOMEONE_REGISTERED_FOR_TOURNAMENT_SESSION_ATTRIBUTE = "isSomeoneRegisteredForTournament";
    public static final String IS_CURRENT_USER_REGISTERED_FOR_TOURNAMENT_SESSION_ATTRIBUTE = "isCurrentUserRegisteredForTournament";
    public static final String TOURNAMENT_SESSION_ATTRIBUTE = "tournament";
    private static final String USER_RATING_PLACE = "userRatingPlace";
    private static final String USERS_COUNT = "usersCount";
    private static final String USER_DTO = "userDto";

    private final TournamentService tournamentService;

    public Optional<Tournament> getNewTournament() {
        return tournamentService.getNewTournament();
    }

    public void setSessionAttributes(HttpServletRequest req, Optional<Tournament> optionalTournament) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER_DTO);
        if (optionalTournament.isPresent()) {
            Tournament tournament = optionalTournament.get();
            session.setAttribute(IS_CURRENT_USER_REGISTERED_FOR_TOURNAMENT_SESSION_ATTRIBUTE, isAlreadyParticipated(user, tournament));
            session.setAttribute(TOURNAMENT_SESSION_ATTRIBUTE, tournament);
            session.setAttribute(USER_RATING_PLACE, tournamentService.getPlaceOfUser(user, tournament.getParticipants()));
            session.setAttribute(USERS_COUNT, tournamentService.getParticipantsListLength(tournament));
            session.setAttribute(IS_SOMEONE_REGISTERED_FOR_TOURNAMENT_SESSION_ATTRIBUTE, !tournament.getParticipants().isEmpty());
        } else {
            session.setAttribute(TOURNAMENT_SESSION_ATTRIBUTE, null);
        }
    }

    public void participate(User user, Tournament tournament) {
        if (!isAlreadyParticipated(user, tournament)) {
            tournamentService.participate(user, tournament);
        }
    }

    public void removeFromTournament(UUID participantId) {
        tournamentService.removeFromTournament(participantId);
    }

    private boolean isAlreadyParticipated(User user, Tournament tournament) {
        TournamentParticipant participant = tournament.getParticipants().stream()
                .filter(p -> p.getUser().getId().equals(user.getId()))
                .findFirst().orElse(null);
        return participant != null;
    }
}
