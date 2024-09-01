package service.tournament.create;

import dto.TournamentDto;
import entity.Tournament;
import org.mapstruct.Mapper;

@Mapper
public interface TournamentMapper {

    Tournament toEntity(TournamentDto tournamentDto);
}
