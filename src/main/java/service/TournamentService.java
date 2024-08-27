package service;

import dao.TournamentDao;
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

    public Optional<Tournament> getNewTournament() {
        return tournamentDao.findNewTournament();
    }

    public void participate(User user, Tournament tournament) {
        tournamentDao.participateUserToTournament(user, tournament);
    }

    public void removeFromTournament(UUID participantId) {
        tournamentDao.removeFromTournament(participantId);
    }

    public int getPlaceOfUser(User user, List<TournamentParticipant> participants) {
        for (int i = 0; i < participants.size(); i++) {
            if (participants.get(i).getUser().getId().equals(user.getId())) {
                return i + 1;
            }
        }
        return 0;
    }

    public int getParticipantsListLength(Tournament tournament) {
        return tournament.getParticipants().size();
    }
}
