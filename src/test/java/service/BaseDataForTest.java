package service;

import entity.Tournament;
import entity.TournamentParticipant;
import entity.User;
import service.extension.ExtensionService;
import service.tournament.TournamentService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseDataForTest {
    protected TournamentService tournamentService;
    protected ExtensionService extensionService;
    protected Tournament tournament;
    protected List<TournamentParticipant> participants;
    protected TournamentParticipant participant1;
    protected TournamentParticipant participant2;
    protected TournamentParticipant participant3;
    protected TournamentParticipant participant4;
    protected TournamentParticipant participant5;
    protected TournamentParticipant participant6;
    protected TournamentParticipant participant7;
    protected TournamentParticipant participant8;
    protected TournamentParticipant participant9;
    protected TournamentParticipant participant10;

    protected TournamentParticipant createMockParticipant(BigDecimal rating) {
        TournamentParticipant participant = mock(TournamentParticipant.class);
        User user = mock(User.class);
        when(user.getRating()).thenReturn(rating);
        when(participant.getUser()).thenReturn(user);
        return participant;
    }
}
