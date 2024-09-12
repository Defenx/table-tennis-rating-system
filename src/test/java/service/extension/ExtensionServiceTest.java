package service.extension;

import entity.TournamentParticipant;
import entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ExtensionServiceTest {

    @InjectMocks
    ExtensionService extensionService;

    @Test
    public void testCalculateAverageRatingIfParticipantsIsEmpty() {
        //then
        BigDecimal result = extensionService.calculateAverageRating(new ArrayList<>());
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    public void testCalculateAverageRatingHalfEven() {
        //given
        var participants = buildParticipants();
        var expected = BigDecimal.valueOf(210);

        //then
        var actual = extensionService.calculateAverageRating(participants);
        assertNotNull(actual);
        assertEquals(expected, actual);
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
                                .rating(BigDecimal.valueOf(200))
                                .build())
                        .build(),
                TournamentParticipant.builder()
                        .user(User.builder()
                                .rating(BigDecimal.valueOf(180))
                                .build())
                        .build()
        );
    }
}
