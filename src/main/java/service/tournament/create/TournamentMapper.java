package service.tournament.create;

import dto.TournamentDto;
import entity.Tournament;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TournamentMapper {

    TournamentMapper INSTANCE = Mappers.getMapper( TournamentMapper.class );
    Tournament toEntity(TournamentDto tournamentDto);
    TournamentDto toDto (Tournament tournament);
}
