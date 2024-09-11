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
import java.util.ArrayList;
import java.util.List;

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

        participants = new ArrayList<>();
        participant1 = createParticipant(BigDecimal.valueOf(250));
        participant2 = createParticipant(BigDecimal.valueOf(200));
        participant3 = createParticipant(BigDecimal.valueOf(180));
        participant4 = createParticipant(BigDecimal.valueOf(165));
        participant5 = createParticipant(BigDecimal.valueOf(170));
        participant6 = createParticipant(BigDecimal.valueOf(165));
        participant7 = createParticipant(BigDecimal.valueOf(160));
        participant8 = createParticipant(BigDecimal.valueOf(150));
        participant9 = createParticipant(BigDecimal.valueOf(145));
        participant10 = createParticipant(BigDecimal.valueOf(135));
        // добавляем в лист учитывая сортировку
        participants.add(participant1);
        participants.add(participant2);
        participants.add(participant3);
        participants.add(participant4);
        participants.add(participant5);
        participants.add(participant6);
        participants.add(participant7);
        participants.add(participant8);
        participants.add(participant9);
        participants.add(participant10);

        tournament = Tournament.builder().participants(participants).build();
    }

    @Test
    public void divideTournamentParticipantsTest() {
        //given
        Match match1 = Match.builder().user1(participant1.getUser()).user2(participant2.getUser()).build();
        Match match2 = Match.builder().user1(participant3.getUser()).user2(participant4.getUser()).build();
        Match match3 = Match.builder().user1(participant5.getUser()).user2(participant6.getUser()).build();
        Match match4 = Match.builder().user1(participant7.getUser()).user2(participant8.getUser()).build();
        Match match5 = Match.builder().user1(participant9.getUser()).user2(participant10.getUser()).build();
        // Предположим, что пары определяются случайно
        List<Match> expectedMatches = new ArrayList<>();
        expectedMatches.add(match1);
        expectedMatches.add(match2);
        expectedMatches.add(match3);
        expectedMatches.add(match4);
        expectedMatches.add(match5);

        //when
        var halfOfParticipantsList = participants.size() / 2;
        var partitionSize = participants.size() % 4 == 0 ? halfOfParticipantsList : halfOfParticipantsList + 1;
        var partitions = ListUtils.partition(participants, partitionSize);

        List<Match> result = tournamentService.divideTournamentParticipants(tournament);

        //then
        // проверка, что участники оказались в турнире
        assertNotNull("Actual result is null", result);
        assertEquals(participants.size() / 2, result.size());
        assertEquals(expectedMatches.size(), result.size());
        result.forEach(match -> {
            assertNotNull("User1 is null", match.getUser1());
            assertNotNull("User2 is null", match.getUser2());
        });

        // проверка, что участники играют со своей половиной игроков
        var top = partitions.get(0).stream().map(TournamentParticipant::getUser).toList();
        var bottom = partitions.get(1).stream().map(TournamentParticipant::getUser).toList();

        assertTrue("Opponents are not on equal terms",result.stream()
                .noneMatch(match ->
                        (top.contains(match.getUser1()) && !top.contains(match.getUser2())) ||
                                (bottom.contains(match.getUser1()) && !bottom.contains(match.getUser2()))
                )
        );
    }
}

