package service.tournament.create;

import dto.TournamentDto;
import entity.Extension;
import entity.Match;
import entity.Tournament;
import entity.TournamentParticipant;
import enums.ExtensionName;
import enums.Status;
import enums.TournamentType;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
public class TournamentMapperTest {

    List<Extension> extensions = List.of(
            Extension.builder().name(ExtensionName.IS_RATING).value("on").build(),
            Extension.builder().name(ExtensionName.PLAYOFF_SETS).value("1").build(),
            Extension.builder().name(ExtensionName.TRAINING_SETS).value("1").build()
    );
    final TournamentDto tournamentDto =
            new TournamentDto(
                    TournamentType.SINGLE_PLAYER,
                    Status.NEW,
                    LocalDate.of(2024, 8, 31),
                    Integer.valueOf(0),
                    extensions
            );

    Tournament tournament =
            new Tournament(
                    UUID.randomUUID(),
                    TournamentType.SINGLE_PLAYER,
                    Status.NEW,
                    LocalDate.of(2024, 8, 31),
                    Integer.valueOf(0),
                    extensions,
                    List.of(TournamentParticipant.builder().build()),
                    List.of(Match.builder().build())
            );

    TournamentMapper tournamentMapper = Mappers.getMapper(TournamentMapper.class);

    Tournament tournamentFromDto = tournamentMapper.toEntity(tournamentDto);


    @Test
    public void comparisonOfTypes() {
        //given
        //when
        //then
        Assert.assertEquals(tournament.getType(), tournamentFromDto.getType());
    }

    @Test
    public void comparisonOfStatus() {
        //given
        //when
        //then
        Assert.assertEquals(tournament.getStatus(), tournamentFromDto.getStatus());
    }

    @Test
    public void comparisonOfDate() {
        //given
        //when
        //then
        Assert.assertEquals(tournament.getDate(), tournamentFromDto.getDate());
    }

    @Test
    public void comparisonOfStage() {
        //given
        //when
        //then
        Assert.assertEquals(tournament.getStage(), tournamentFromDto.getStage());
    }

    @Test
    public void comparisonOfExtension() {
        //given
        //when
        //then
        Assert.assertEquals(tournament.getExtensions(), tournamentFromDto.getExtensions());
    }
}