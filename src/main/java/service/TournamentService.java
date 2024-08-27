package service;

import dao.TournamentDao;
import entity.Tournament;
import entity.User;
import enums.Status;
import enums.TournamentType;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
