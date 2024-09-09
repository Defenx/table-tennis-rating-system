package service;

import dao.TournamentDao;
import dao.TournamentParticipantDao;
import entity.Tournament;
import entity.TournamentParticipant;
import entity.User;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class TournamentService {
    private final TournamentDao tournamentDao;
    private final TournamentParticipantDao tournamentParticipantDao;
    private final UserService userService;

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
        tournamentDao.runTournament(tournament);
    }

    public List<Tournament> getLaunchedTournaments() {
        return tournamentDao.getLaunchedTournaments();
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
}
