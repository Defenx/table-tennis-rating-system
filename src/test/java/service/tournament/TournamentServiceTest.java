package service.tournament;

import dao.TournamentDao;
import dao.TournamentParticipantDao;
import entity.Match;
import entity.Tournament;
import entity.TournamentParticipant;
import org.apache.commons.collections4.ListUtils;
import org.junit.Before;
import org.junit.Test;
import service.BaseDataForTest;
import service.TransactionHandler;
import service.extension.ExtensionService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class TournamentServiceTest extends BaseDataForTest {

    @Before
    public void setUp() {
        TournamentDao tournamentDao = mock(TournamentDao.class);
        TournamentParticipantDao tournamentParticipantDao = mock(TournamentParticipantDao.class);
        TransactionHandler transactionHandler = mock(TransactionHandler.class);
        ExtensionService extensionService = mock(ExtensionService.class);
        tournamentService = new TournamentService(tournamentDao, tournamentParticipantDao, transactionHandler, extensionService);

        participants = List.of(
                participant1 = createParticipant(BigDecimal.valueOf(250)),
                participant2 = createParticipant(BigDecimal.valueOf(200)),
                participant3 = createParticipant(BigDecimal.valueOf(180)),
                participant4 = createParticipant(BigDecimal.valueOf(165)),
                participant5 = createParticipant(BigDecimal.valueOf(170)),
                participant6 = createParticipant(BigDecimal.valueOf(165)),
                participant7 = createParticipant(BigDecimal.valueOf(160)),
                participant8 = createParticipant(BigDecimal.valueOf(150)),
                participant9 = createParticipant(BigDecimal.valueOf(145)),
                participant10 = createParticipant(BigDecimal.valueOf(135))
        );

        tournament = Tournament.builder().participants(participants).build();
    }

    @Test
    public void testDivideTournamentParticipantsAllParticipantsInGame() {
        //when
        List<Match> result = tournamentService.divideTournamentParticipants(tournament);

        //then
        assertNotNull("Actual result is null", result);
        assertEquals("Unexpected number of matches", participants.size() / 2, result.size());

        var allPlayersInMatches = result.stream()
                .flatMap(match -> Stream.of(match.getUser1(), match.getUser2()))
                .collect(Collectors.toSet());
        var allPlayers = participants.stream()
                .map(TournamentParticipant::getUser)
                .collect(Collectors.toSet());
        assertEquals("Not all players are in matches", allPlayers, allPlayersInMatches);
    }

    @Test
    public void testDivideTournamentParticipantsUsersInRightPlace() {
        //when
        List<Match> result = tournamentService.divideTournamentParticipants(tournament);

        //then
        var halfOfParticipantsList = participants.size() / 2;
        var partitionSize = participants.size() % 4 == 0 ? halfOfParticipantsList : halfOfParticipantsList + 1;
        var partitions = ListUtils.partition(participants, partitionSize);
        var topHalf = partitions.get(0).stream().map(TournamentParticipant::getUser).toList();

        result.forEach(match -> {
            boolean user1InTop = topHalf.contains(match.getUser1());
            boolean user2InTop = topHalf.contains(match.getUser2());
            assertTrue("Match has participants from different partitions.", user1InTop == user2InTop);
        });
    }
}
