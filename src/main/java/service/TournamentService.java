package service;

import dao.TournamentDao;
import entity.Tournament;
import entity.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class TournamentService {
    private final TournamentDao tournamentDao;
    public Optional<Tournament> getNewTournament() {
        return tournamentDao.findNewTournament();
    }

    public void participate(User user, Tournament tournament) {
        tournamentDao.participateUserToTournament(user,tournament);
    }
}
