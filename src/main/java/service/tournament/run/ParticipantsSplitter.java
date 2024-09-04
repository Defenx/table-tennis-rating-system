package service.tournament.run;

import entity.Match;
import entity.Tournament;
import entity.TournamentParticipant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParticipantsSplitter {
    public static List<Match> splitParticipants(Tournament tournament) {
        List<Match> splitParticipantsList = new ArrayList<>();
        List<TournamentParticipant> participants = tournament.getParticipants();
        Collections.shuffle(participants);
        for (int i = 0; i < participants.size(); i = i + 2) {
            Match match;
            if ((i + 1) < (participants.size())) {
                match = Match.builder()
                        .tournament(tournament)
                        .user1(participants.get(i).getUser())
                        .user2(participants.get(i + 1).getUser())
                        .build();

            } else {
                match = Match.builder()
                        .user1(participants.get(i).getUser())
                        .build();
            }
            splitParticipantsList.add(match);
        }
        return splitParticipantsList;
    }
}
