package service;

import dao.TournamentDao;
import dao.TournamentParticipantDao;
import entity.Tournament;
import entity.TournamentParticipant;
import entity.User;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class TournamentService {
    private final TournamentDao tournamentDao;
    private final TournamentParticipantDao tournamentParticipantDao;

    public Optional<Tournament> getNewTournament() {
        return tournamentDao.findTournamentWhereStatusIsNew();
    }

    public void participate(User user, Tournament tournament) {
        tournamentParticipantDao.participateUserToTournament(user, tournament);
    }

    public void removeFromTournament(UUID participantId) {
        tournamentParticipantDao.removeFromTournament(participantId);
    }

    public void withdrawFromTheTournament(User user, Tournament tournament) {
        if (user != null && tournament != null) {
            for (TournamentParticipant participant : tournament.getParticipants()) {
                if (participant.getUser().getId().equals(user.getId())) {
                    removeFromTournament(participant.getId());
                    break;
                }
            }
        } else System.err.println("withdrawFromTheTournament ERROR");
    }

    public int getPlaceOfUser(User user, List<TournamentParticipant> participants) {
        if (user != null && participants != null) {
            for (int i = 0; i < participants.size(); i++) {
                if (participants.get(i).getUser().getId().equals(user.getId())) {
                    return i + 1;
                }
            }
        }
        return 0;
    }

    public int getParticipantsListLength(Tournament tournament) {
        return tournament.getParticipants().size();
    }

    public void deleteTournament() {
        var tournamentWhereStatusIsNew = tournamentDao.findTournamentWhereStatusIsNew();
        tournamentWhereStatusIsNew.ifPresent(tournamentDao::deleteTournament);
    }

    public boolean isAlreadyParticipated(User user, Tournament tournament) {
        if (user != null && tournament != null) {
            return tournament.getParticipants().stream()
                    .anyMatch(participant -> participant.getUser().getId().equals(user.getId()));
        } else {
            System.err.println("isAlreadyParticipated ERROR");
            return false;
        }
    }
}
