package service.tournament.create;

import dao.TournamentDao;
import dto.TournamentDto;
import entity.Tournament;
import enums.Status;
import lombok.AllArgsConstructor;



@AllArgsConstructor
public class TournamentCreateService {

    private final TournamentDao tournamentDao;

    public void addTournament(TournamentDto tournamentDto) {
        tournamentDao.create(
                TournamentMapper.INSTANCE.toEntity(tournamentDto)
        );
    }
}
