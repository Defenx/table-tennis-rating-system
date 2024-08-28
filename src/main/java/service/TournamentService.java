package service;

import dao.TournamentDao;
import entity.Tournament;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TournamentService {
    private final TournamentDao tournamentDao;

    public void add(Tournament tournament) {
        tournamentDao.create(tournament);
    }
}
