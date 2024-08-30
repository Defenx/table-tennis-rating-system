package service.tournament.create;

import dao.TournamentDao;
import dto.TournamentDto;
import entity.Tournament;
import enums.Status;
import lombok.AllArgsConstructor;



@AllArgsConstructor
public class TournamentCreateService {

    private final TournamentDao tournamentDao;
    private TournamentMapper tournamentMapper;

    public void addTournament(TournamentDto tournamentDto) {
        tournamentDao.create(
                tournamentMapper.toEntity(tournamentDto)
        );
    }
}
