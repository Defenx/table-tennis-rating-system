package service.extension;

import entity.TournamentParticipant;
import org.junit.Before;
import org.junit.Test;
import service.BaseDataForTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ExtensionServiceTest extends BaseDataForTest {
    BigDecimal v1;
    BigDecimal v2;
    BigDecimal v3;
    BigDecimal v4;
    BigDecimal participantsSize;

    @Before
    public void setUp() {
        extensionService = new ExtensionService();
        v1 = BigDecimal.valueOf(250);
        v2 = BigDecimal.valueOf(200);
        v3 = BigDecimal.valueOf(180);
        v4 = BigDecimal.valueOf(160);

        participants = new ArrayList<>();
        participant1 = createParticipant(v1);
        participant2 = createParticipant(v2);
        participant3 = createParticipant(v3);
        participant4 = createParticipant(v4);

        participants.add(participant1);
        participants.add(participant2);
        participants.add(participant3);
        participants.add(participant4);

        participantsSize = BigDecimal.valueOf(participants.size());
    }

    @Test
    public void testCalculateAverageRatingIfParticipantsIsEmpty() {
        //when
        BigDecimal result = extensionService.calculateAverageRating(new ArrayList<>());

        //then
        assertNotNull(result);
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    public void testCalculateAverageRatingIfParticipantAlone() {
        //given
        BigDecimal bigDecimal = BigDecimal.valueOf(150);
        TournamentParticipant participant = createParticipant(bigDecimal);
        List<TournamentParticipant> participants = List.of(participant);

        //when
        BigDecimal result = extensionService.calculateAverageRating(participants);

        //then
        assertNotNull(result);
        assertEquals(bigDecimal, result);
    }

    @Test
    public void testCalculateAverageRatingHalfEven() {
        //given
        List<TournamentParticipant> participants = List.of(participant1, participant2, participant3, participant4);

        //when
        BigDecimal expectedAverage = (v1.add(v2).add(v3).add(v4)).divide(participantsSize, RoundingMode.HALF_EVEN);
        BigDecimal result = extensionService.calculateAverageRating(participants);

        //then
        assertNotNull(result);
        assertEquals(expectedAverage, result);
    }
}
