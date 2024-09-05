package service;

import dao.TournamentDao;
import dao.TournamentParticipantDao;
import entity.Match;
import entity.Tournament;
import entity.TournamentParticipant;
import entity.User;
import lombok.RequiredArgsConstructor;
import service.tournament.run.ParticipantsSplitter;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class TournamentService {
    private final TournamentDao tournamentDao;
    private final TournamentParticipantDao tournamentParticipantDao;

    public Tournament getTournamentById(UUID tournamentId) {
        return tournamentDao.getTournamentById(tournamentId);
    }

    public List<Tournament> getNewTournaments() {
        return tournamentDao.findTournamentsWhereStatusIsNew();
    }

    public void participate(User user, Tournament tournament) {
        if (!isAlreadyParticipated(user, tournament)) {
            tournamentParticipantDao.participateUserToTournament(user, tournament);
        }
    }

    public void removeFromTournament(UUID participantId) {
        tournamentParticipantDao.removeFromTournament(participantId);
    }

    public void withdrawFromTheTournament(User user, Tournament tournament) {
        for (TournamentParticipant participant : tournament.getParticipants()) {
            if (participant.getUser().getId().equals(user.getId())) {
                removeFromTournament(participant.getId());
                break;
            }
        }
    }

    public void deleteTournament(Tournament tournament) {
        if (tournament != null) {
            tournamentDao.deleteTournament(tournament);
        }
    }

    public boolean isAlreadyParticipated(User user, Tournament tournament) {
        return tournament.getParticipants().stream()
                .anyMatch(participant -> participant.getUser().getId().equals(user.getId()));
    }

    public void runTournament(Tournament tournament) {
        List<Match> matches = ParticipantsSplitter.splitParticipants(tournament);
        tournamentDao.runTournament(tournament, matches);
        Match incompleteMatch = tournament.getMatches().stream().filter(match -> match.getUser2() == null).findFirst().orElse(null);
        if (incompleteMatch != null) {
            User magicUser = User.builder()
                    .firstname("user")
                    .surname("Magic")
                    .build();
            incompleteMatch.setUser2(magicUser);
        }
    }

    public List<Tournament> getLaunchedTournaments() {
        return tournamentDao.getLaunchedTournaments();
    }
}
