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
        tournamentDao.create(Tournament.builder()
                        .type(tournamentDto.type())
                        .date(tournamentDto.data())
                        .extensions(tournamentDto.extensions())
                        .status(Status.NEW)
                        .stage(0)
                .build());
    }
}
