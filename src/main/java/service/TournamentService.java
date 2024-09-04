package service;

import dao.MatchDao;
import dao.TournamentDao;
import dao.TournamentParticipantDao;
import entity.Match;
import entity.Tournament;
import entity.TournamentParticipant;
import entity.User;
import lombok.RequiredArgsConstructor;
import service.tournament.run.ParticipantsSplitter;

import java.util.*;

@RequiredArgsConstructor
public class TournamentService {
    private final TournamentDao tournamentDao;
    private final TournamentParticipantDao tournamentParticipantDao;
    private final MatchDao matchDao;

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

    public Optional<User> runTournament(Tournament tournament) {
        List<Match> matches = ParticipantsSplitter.splitParticipants(tournament);
        tournamentDao.runTournament(tournament, matches);
        Match incompleteMatch = tournament.getMatches().stream().filter(match -> match.getUser2() == null).findFirst().orElse(null);
        User magicUser = null;
        if (incompleteMatch != null) {
            magicUser = incompleteMatch.getUser1();
            tournament.getMatches().remove(incompleteMatch);
            TournamentParticipant magicParticipant = tournamentParticipantDao.findByUserID(magicUser.getId());
            tournamentParticipantDao.removeFromTournament(magicParticipant.getId());
        }
        return magicUser == null ? Optional.empty() : Optional.of(magicUser);



    }

    public List<Match> getMatchesByTournamentID(UUID tournamentID) {
        return matchDao.getMatchesByTournamentId(tournamentID);
    }
}
