package service.home;

import entity.Tournament;
import entity.TournamentParticipant;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import service.TournamentService;
import servlet.Route;

import java.io.IOException;
import java.util.Comparator;
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

    public void setSessionAttributes(HttpServletRequest req, HttpServletResponse resp, Tournament tournament) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER_DTO);
        if (tournament != null) {
            tournament.getParticipants()
                    .sort(Comparator.comparing((TournamentParticipant tp) -> tp.getUser().getRating()).reversed());
            req.setAttribute(TOURNAMENT_SESSION_ATTRIBUTE, tournament);
            req.setAttribute(IS_CURRENT_USER_REGISTERED_FOR_TOURNAMENT_SESSION_ATTRIBUTE, isAlreadyParticipated(user, tournament));
            req.setAttribute(IS_SOMEONE_REGISTERED_FOR_TOURNAMENT_SESSION_ATTRIBUTE, !tournament.getParticipants().isEmpty());
            req.setAttribute(USER_RATING_PLACE, tournamentService.getPlaceOfUser(user, tournament.getParticipants()));
            req.setAttribute(USERS_COUNT, tournamentService.getParticipantsListLength(tournament));
            req.getRequestDispatcher(Route.HOME_JSP).forward(req, resp);
        } else {
            req.setAttribute(TOURNAMENT_SESSION_ATTRIBUTE, null);
            req.getRequestDispatcher(Route.HOME_JSP).forward(req, resp);
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

    public void withdrawFromTheTournament(User user, Tournament tournament) {
        for (TournamentParticipant participant : tournament.getParticipants()) {
            if (participant.getUser().getId().equals(user.getId())) {
                removeFromTournament(participant.getId());
                break;
            }
        }
    }
}
