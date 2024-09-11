package service.tournament;

import dao.TournamentDao;
import dao.TournamentParticipantDao;
import entity.Match;
import entity.Tournament;
import org.junit.Before;
import org.junit.Test;
import service.BaseDataForTest;
import service.TransactionHandler;
import service.extension.ExtensionService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TournamentServiceTest extends BaseDataForTest {

    @Before
    public void setUp() {
        TournamentDao tournamentDao = mock(TournamentDao.class);
        TournamentParticipantDao tournamentParticipantDao = mock(TournamentParticipantDao.class);
        TransactionHandler transactionHandler = mock(TransactionHandler.class);
        ExtensionService extensionService = mock(ExtensionService.class);
        tournamentService = new TournamentService(tournamentDao, tournamentParticipantDao, transactionHandler, extensionService); // Замените на вашу реальную реализацию

        tournament = mock(Tournament.class);
        participants = new ArrayList<>();
        participant1 = createMockParticipant(BigDecimal.valueOf(250));
        participant2 = createMockParticipant(BigDecimal.valueOf(200));
        participant3 = createMockParticipant(BigDecimal.valueOf(180));
        participant4 = createMockParticipant(BigDecimal.valueOf(165));
        participant5 = createMockParticipant(BigDecimal.valueOf(170));
        participant6 = createMockParticipant(BigDecimal.valueOf(165));
        participant7 = createMockParticipant(BigDecimal.valueOf(160));
        participant8 = createMockParticipant(BigDecimal.valueOf(150));
        participant9 = createMockParticipant(BigDecimal.valueOf(145));
        participant10 = createMockParticipant(BigDecimal.valueOf(135));

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
    }

    @Test
    public void testTournamentParticipantSeparator() {
        //given
        Match match1 = Match.builder().user1(participant1.getUser()).user2(participant2.getUser()).build();
        Match match2 = Match.builder().user1(participant3.getUser()).user2(participant4.getUser()).build();
        Match match3 = Match.builder().user1(participant5.getUser()).user2(participant6.getUser()).build();
        Match match4 = Match.builder().user1(participant7.getUser()).user2(participant8.getUser()).build();
        Match match5 = Match.builder().user1(participant9.getUser()).user2(participant10.getUser()).build();

        // Предположим, что пары определяются случайно т.к Collections.shuffle() не нуждается в проверке

        List<Match> expectedMatches = new ArrayList<>();
        expectedMatches.add(match1);
        expectedMatches.add(match2);
        expectedMatches.add(match3);
        expectedMatches.add(match4);
        expectedMatches.add(match5);

        //when
        when(tournament.getParticipants()).thenReturn(participants);
        List<Match> result = tournamentService.tournamentParticipantSeparator(tournament);

        //then
        assertNotNull("Actual result is null", result);
        assertEquals(participants.size() / 2, result.size());
        assertEquals(expectedMatches.size(), result.size());
        for (Match match : result) {
            assertNotNull("User1 is null", match.getUser1());
            assertNotNull("User2 is null", match.getUser2());
        }
    }
}

