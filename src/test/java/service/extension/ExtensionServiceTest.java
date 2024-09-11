package service.extension;

import entity.TournamentParticipant;
import org.junit.Before;
import org.junit.Test;
import service.BaseDataForTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ExtensionServiceTest extends BaseDataForTest {

    @Before
    public void setUp() {
        extensionService = new ExtensionService();

        participants = new ArrayList<>();
        participant1 = createParticipant(BigDecimal.valueOf(250));
        participant2 = createParticipant(BigDecimal.valueOf(200));
        participant3 = createParticipant(BigDecimal.valueOf(180));
        participant4 = createParticipant(BigDecimal.valueOf(160));

        participants.add(participant1);
        participants.add(participant2);
        participants.add(participant3);
        participants.add(participant4);
    }

    @Test
    public void testCalculateAverageRating() {
        //given
        List<TournamentParticipant> participants = List.of(participant1, participant2, participant3, participant4);

        //when
        BigDecimal expectedAverage = BigDecimal.valueOf(198); // (250 + 200 + 180 + 160) / 4 RoundingMode.HALF_EVEN
        BigDecimal result = extensionService.calculateAverageRating(participants);

        //then
        assertNotNull(result);
        assertEquals(expectedAverage, result);
    }
}
