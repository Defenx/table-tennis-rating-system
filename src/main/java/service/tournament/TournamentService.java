package service.tournament;

import dao.TournamentDao;
import dao.TournamentParticipantDao;
import entity.*;
import enums.ExtensionName;
import enums.Status;
import enums.TournamentType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import service.TransactionHandler;
import service.extension.ExtensionService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class TournamentService {
    private final TournamentDao tournamentDao;
    private final TournamentParticipantDao tournamentParticipantDao;
    private final TransactionHandler transactionHandler;
    private final ExtensionService extensionService;


    public Tournament getTournamentById(UUID tournamentId) {
        return tournamentDao.getTournamentById(tournamentId);
    }

    public List<Tournament> getNewTournaments() {
        return tournamentDao.findTournamentsWithStatus(Status.NEW);
    }
    public List<Tournament> getProcessingTournaments() {
        return tournamentDao.findTournamentsWithStatus(Status.PROCESSING);
    }
    public String translateTournamentType(String type) {
        try {
            TournamentType tournamentType = TournamentType.valueOf(type.toUpperCase());
            return tournamentType.getDisplayName();
        } catch (IllegalArgumentException e) {
            return type;
        }
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
        transactionHandler.executeWithTransaction(session -> {
            tournament.setStage(1);
            List<Match> matches = divideTournamentParticipants(tournament);
            tournament.getExtensions().add(Extension.builder()
                    .tournament(tournament)
                    .name(ExtensionName.AVERAGE_RATING)
                    .value(String.valueOf(extensionService.calculateAverageRating(tournament.getParticipants())))
                    .build());
            tournament.setStatus(Status.PROCESSING);
            tournament.setMatches(matches);
            session.merge(tournament);
        });
    }

    public List<Match> divideTournamentParticipants(Tournament tournament) {
        var participants = tournament.getParticipants().stream()
                .sorted(Comparator.comparing((TournamentParticipant p) -> p.getUser().getRating()).reversed())
                .toList();

        var halfOfParticipantsList = participants.size() / 2;
        var partitionSize = participants.size() % 4 == 0 ? halfOfParticipantsList : halfOfParticipantsList + 1;

        var partitions = ListUtils.partition(participants, partitionSize);

        return partitions.stream()
                .flatMap(partition -> distributeParticipantsForMatches(partition, tournament).stream())
                .toList();
    }

    private List<Match> distributeParticipantsForMatches(List<TournamentParticipant> participantsImmutable, Tournament tournament) {
        var participants = new ArrayList<>(participantsImmutable);
        Collections.shuffle(participants);

        return IntStream.range(0, participants.size() / 2)
                .mapToObj(i -> buildMatch(participants, tournament, i))
                .collect(Collectors.toList());
    }

    private Match buildMatch(List<TournamentParticipant> participants, Tournament tournament, int i) {
        return Match.builder()
                .tournament(tournament)
                .user1(participants.get(i * 2).getUser())
                .user2(participants.get(i * 2 + 1).getUser())
                .stage(tournament.getStage())
                .build();
    }
}
