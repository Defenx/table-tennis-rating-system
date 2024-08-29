package service.tournament.create;

import dao.TournamentDao;
import dto.TournamentDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TournamentCreateService {

    private final TournamentDao tournamentDao;
    private final TournamentMapper tournamentMapper;

    public void createTournament(TournamentDto tournamentDto) {
        var tournament = tournamentMapper.toEntity(tournamentDto);
        tournamentDao.create(tournament);
    }
}
