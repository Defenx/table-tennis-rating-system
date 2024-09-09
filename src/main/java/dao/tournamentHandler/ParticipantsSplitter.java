package dao.tournamentHandler;

import entity.Match;
import entity.Tournament;
import entity.TournamentParticipant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ParticipantsSplitter {

    public static List<Match> distribute(Tournament tournament) {
        List<TournamentParticipant> participants = tournament.getParticipants();
        if (!participants.isEmpty() && participants.size() % 2 == 0) {
            List<Match> listOfMatches = new ArrayList<>();
            participants.sort(Comparator.comparing((TournamentParticipant tp) -> tp.getUser().getRating()).reversed());

            List<TournamentParticipant> firstPartParticipants;
            List<TournamentParticipant> secondPartParticipants;

            if ((participants.size() / 2) % 2 != 0) {
                firstPartParticipants = participants.subList(0, participants.size() / 2 + 1);
                secondPartParticipants = participants.subList(participants.size() / 2 + 1, participants.size());
            } else {
                firstPartParticipants = participants.subList(0, participants.size() / 2);
                secondPartParticipants = participants.subList(participants.size() / 2, participants.size());
            }

            Collections.shuffle(firstPartParticipants);
            Collections.shuffle(secondPartParticipants);
            listOfMatches.addAll(distributeParticipants(firstPartParticipants, tournament));
            listOfMatches.addAll(distributeParticipants(secondPartParticipants, tournament));

            return listOfMatches;

        } else
            throw new RuntimeException("The number of participants is not even");
    }

    private static List<Match> distributeParticipants(List<TournamentParticipant> participants, Tournament tournament) {
        List<Match> listOfMatches = new ArrayList<>();
        for (int i = 0; i < participants.size(); i = i + 2) {
            Match match = Match.builder()
                    .tournament(tournament)
                    .user1(participants.get(i).getUser())
                    .user2(participants.get(i + 1).getUser())
                    .build();
            listOfMatches.add(match);
        }
        return listOfMatches;
    }
}
