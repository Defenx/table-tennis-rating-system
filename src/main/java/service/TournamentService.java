package service;

import dao.TournamentDao;
import entity.Tournament;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class TournamentService {
    private final TournamentDao tournamentDao;
    public Optional<Tournament> getNewTournament() {
        return tournamentDao.findNewTournament();
    }
}
