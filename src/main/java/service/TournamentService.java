package service;

import dao.TournamentDao;
import dao.TournamentParticipantDao;
import entity.*;
import enums.ExtensionName;
import enums.Status;
import lombok.RequiredArgsConstructor;
import service.tournament.TransactionHandler;

import java.util.*;

@RequiredArgsConstructor
public class TournamentService {
    private final TournamentDao tournamentDao;
    private final TournamentParticipantDao tournamentParticipantDao;
    private final UserService userService;
    private final TransactionHandler transactionHandler;

    public Tournament getTournamentById(UUID tournamentId) {
        return tournamentDao.getTournamentById(tournamentId);
    }

    public List<Tournament> getNewTournaments() {
        return tournamentDao.findTournamentsWhereStatusIsNew();
    }

    public void participate(User user, Tournament tournament) {
        if (!isAlreadyParticipated(user, tournament)) {
            tournamentParticipantDao.participateUserToTournament(user, tournament);
        }
    }

    public void removeFromTournament(UUID participantId) {
        tournamentParticipantDao.removeFromTournament(participantId);
    }

    public void withdrawFromTheTournament(User user, Tournament tournament) {
        for (TournamentParticipant participant : tournament.getParticipants()) {
            if (participant.getUser().getId().equals(user.getId())) {
                removeFromTournament(participant.getId());
                break;
            }
        }
    }

    public void deleteTournament(Tournament tournament) {
        if (tournament != null) {
            tournamentDao.deleteTournament(tournament);
        }
    }

    public boolean isAlreadyParticipated(User user, Tournament tournament) {
        return tournament.getParticipants().stream()
                .anyMatch(participant -> participant.getUser().getId().equals(user.getId()));
    }

    public void runTournament(Tournament tournament) {
        if (tournament.getParticipants().size() % 2 != 0) {
            addMagicUserToTournament(tournament);
        }
        transactionHandler.executeWithTransaction(session -> {
            List<Match> matches = matchmaker(tournament);
            tournament.getExtensions().add(Extension.builder()
                    .tournament(tournament)
                    .name(ExtensionName.AVERAGE_RATING)
                    .value(String.valueOf(calculateAverageRating(tournament.getParticipants())))
                    .build());
            tournament.setStage(1);
            tournament.setStatus(Status.PROCESSING);
            tournament.setMatches(matches);
            session.merge(tournament);
        });
    }

    private void addMagicUserToTournament(Tournament tournament) {
        int rating = (int) tournament.getParticipants().stream()
                .map(p -> p.getUser().getRating())
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .average()
                .orElseThrow(() -> new RuntimeException("Unable to calculate average rating"));

        Optional<User> magicUser = userService.findByEmail("fake@mail.com");
        if (magicUser.isPresent()) {
            userService.updateUserRating(magicUser.get(), rating);
            tournamentParticipantDao.participateUserToTournament(magicUser.get(), tournament);
        }
    }

    public List<Match> matchmaker(Tournament tournament) {
        List<TournamentParticipant> participants = tournament.getParticipants();
        int size = participants.size();
        if (size != 0 && size % 2 == 0) {
            List<Match> listOfMatches = new ArrayList<>();
            participants.sort(Comparator.comparing((TournamentParticipant tp) -> tp.getUser().getRating()).reversed());

            boolean even = (size / 2) % 2 == 0;
            List<TournamentParticipant> firstPart = participants.subList(0, even ? (size / 2) : (size / 2) + 1);
            List<TournamentParticipant> secondPart = participants.subList(even ? (size / 2) : (size / 2) + 1, size);

            listOfMatches.addAll(distributeParticipantsForMatches(firstPart, tournament));
            listOfMatches.addAll(distributeParticipantsForMatches(secondPart, tournament));

            return listOfMatches;
        } else
            throw new RuntimeException("The number of participants is not even");
    }

    public List<Match> distributeParticipantsForMatches(List<TournamentParticipant> participants, Tournament tournament) {
        Collections.shuffle(participants);
        List<Match> listOfMatches = new ArrayList<>();
        for (int i = 0; i < participants.size(); i = i + 2) {
            listOfMatches.add(Match.builder()
                    .tournament(tournament)
                    .user1(participants.get(i).getUser())
                    .user2(participants.get(i + 1).getUser())
                    .build());
        }
        return listOfMatches;
    }

    public double calculateAverageRating(List<TournamentParticipant> participants) {
        return participants.stream()
                .map(p -> p.getUser().getRating())
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .average()
                .orElseThrow(() -> new RuntimeException("Unable to calculate average rating"));
    }
}
