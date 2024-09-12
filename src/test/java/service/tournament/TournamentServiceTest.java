package service.tournament;

import entity.Match;
import entity.Tournament;
import entity.TournamentParticipant;
import entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class TournamentServiceTest {

    @InjectMocks
    TournamentService tournamentService;

    @Test
    public void testDivideTournamentParticipantsAmountOfMatchesIsOdd() {
        //given
        var participants = buildParticipants().stream()
                .sorted(Comparator.comparing((TournamentParticipant p) -> p.getUser().getRating()).reversed())
                .toList();

        var strongerParticipants = participants.subList(0, 6).stream().map(TournamentParticipant::getUser).toList();

        //then
        List<Match> actual = tournamentService.divideTournamentParticipants(Tournament.builder()
                .participants(participants)
                .build()
        );

        actual.forEach(match -> {
            assertNotNull(match.getUser1());
            assertNotNull(match.getUser2());
            boolean user1IsIn = strongerParticipants.contains(match.getUser1());
            boolean user2IsIn = strongerParticipants.contains(match.getUser2());
            assertEquals("Match has participants from different partitions.", user1IsIn, user2IsIn);
        });
    }

    @Test
    public void testDivideTournamentParticipantsAmountOfMatchesIsEven() {
        //given
        var participants = buildParticipants().stream()
                .sorted(Comparator.comparing((TournamentParticipant p) -> p.getUser().getRating()).reversed())
                .toList().subList(0, 8);

        var strongerParticipants = participants.subList(0, 4).stream().map(TournamentParticipant::getUser).toList();

        //then
        List<Match> actual = tournamentService.divideTournamentParticipants(Tournament.builder()
                .participants(participants)
                .build()
        );

        actual.forEach(match -> {
            assertNotNull(match.getUser1());
            assertNotNull(match.getUser2());
            boolean user1IsIn = strongerParticipants.contains(match.getUser1());
            boolean user2IsIn = strongerParticipants.contains(match.getUser2());
            assertEquals("Match has participants from different partitions.", user1IsIn, user2IsIn);
        });
    }

    private static List<TournamentParticipant> buildParticipants() {
        return List.of(
                TournamentParticipant.builder()
                        .user(User.builder()
                                .rating(BigDecimal.valueOf(250))
                                .build())
                        .build(),
                TournamentParticipant.builder()
                        .user(User.builder()
                                .rating(BigDecimal.valueOf(220))
                                .build())
                        .build(),
                TournamentParticipant.builder()
                        .user(User.builder()
                                .rating(BigDecimal.valueOf(200))
                                .build())
                        .build(),
                TournamentParticipant.builder()
                        .user(User.builder()
                                .rating(BigDecimal.valueOf(220))
                                .build())
                        .build(),
                TournamentParticipant.builder()
                        .user(User.builder()
                                .rating(BigDecimal.valueOf(210))
                                .build())
                        .build(),
                TournamentParticipant.builder()
                        .user(User.builder()
                                .rating(BigDecimal.valueOf(190))
                                .build())
                        .build(),
                TournamentParticipant.builder()
                        .user(User.builder()
                                .rating(BigDecimal.valueOf(230))
                                .build())
                        .build(),
                TournamentParticipant.builder()
                        .user(User.builder()
                                .rating(BigDecimal.valueOf(180))
                                .build())
                        .build(),
                TournamentParticipant.builder()
                        .user(User.builder()
                                .rating(BigDecimal.valueOf(170))
                                .build())
                        .build(),
                TournamentParticipant.builder()
                        .user(User.builder()
                                .rating(BigDecimal.valueOf(160))
                                .build())
                        .build()
        );
    }
}
