package service.home;

import entity.Tournament;
import entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import service.TournamentService;
import servlet.Attributes;

import java.util.Optional;

@RequiredArgsConstructor
public class TournamentHelperService {
    public static final String IS_SOMEONE_REGISTERED_FOR_TOURNAMENT_SESSION_ATTRIBUTE = "isSomeoneRegisteredForTournament";
    public static final String IS_CURRENT_USER_REGISTERED_FOR_TOURNAMENT_SESSION_ATTRIBUTE = "isCurrentUserRegisteredForTournament";
    private final TournamentService tournamentService;
    public Optional<Tournament> getNewTournament() {
        return tournamentService.getNewTournament();
    }
    public void setSessionAttributes(HttpServletRequest req, Optional<Tournament> tournament) {
        HttpSession session = req.getSession();
        if(tournament.isPresent()) {
            session.setAttribute(Attributes.TOURNAMENT_SESSION_ATTRIBUTE.toString(), tournament.get());
            session.setAttribute(IS_SOMEONE_REGISTERED_FOR_TOURNAMENT_SESSION_ATTRIBUTE, !tournament.get().getParticipants().isEmpty());
        } else {
            session.setAttribute(Attributes.TOURNAMENT_SESSION_ATTRIBUTE.toString(),null);
        }
    }

    public void participate(HttpServletRequest req) {
        HttpSession session = req.getSession();
        var tournament = (Tournament) session.getAttribute(Attributes.TOURNAMENT_SESSION_ATTRIBUTE.toString());
        User user = (User) session.getAttribute(Attributes.USER_DTO_SESSION_ATTRIBUTE.toString());
        session.setAttribute(IS_CURRENT_USER_REGISTERED_FOR_TOURNAMENT_SESSION_ATTRIBUTE, true);
        tournamentService.participate(user, tournament);
    }
}
