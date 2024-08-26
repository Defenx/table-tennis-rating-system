package service.home;

import entity.Tournament;
import entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import service.TournamentService;

import java.util.Optional;

@RequiredArgsConstructor
public class TournamentHelperService {
    public static final String IS_SOMEONE_REGISTERED_FOR_TOURNAMENT_SESSION_ATTRIBUTE = "isSomeoneRegisteredForTournament";
    public static final String TOURNAMENT_SESSION_ATTRIBUTE = "tournament";
    private final TournamentService tournamentService;
    public Optional<Tournament> getNewTournament() {
        return tournamentService.getNewTournament();
    }
    public void setSessionAttributes(HttpServletRequest req, Optional<Tournament> tournament) {
        HttpSession session = req.getSession();
        if(tournament.isPresent()) {
            session.setAttribute(TOURNAMENT_SESSION_ATTRIBUTE, tournament.get());
            session.setAttribute(IS_SOMEONE_REGISTERED_FOR_TOURNAMENT_SESSION_ATTRIBUTE, !tournament.get().getParticipants().isEmpty());
        } else {
            session.setAttribute(TOURNAMENT_SESSION_ATTRIBUTE,null);
        }
    }

    public void participate(User user, Tournament tournament) {
        tournamentService.participate(user, tournament);
    }
}
