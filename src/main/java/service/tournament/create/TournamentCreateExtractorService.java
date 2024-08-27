package service.tournament.create;

import dto.TournamentDto;
import entity.Extension;
import enums.ExtensionName;
import enums.TournamentType;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.List;

public class TournamentCreateExtractorService implements TournamentCreateExtractor {
    @Override
    public TournamentDto extract(HttpServletRequest request) {
        TournamentType type = TournamentType.valueOf(request.getParameter("type"));
        LocalDate date = LocalDate.parse(request.getParameter("date"));

        Extension numberOfSetInWorkout = new Extension();
        numberOfSetInWorkout.setName(ExtensionName.NUMBER_OF_SET_IN_WORKOUT);
        String numberOfSetInWorkoutFromReq = request.getParameter("numberOfSetInWorkout");
        numberOfSetInWorkout.setValue(numberOfSetInWorkoutFromReq);

        Extension numberOfSetInPlayOff = new Extension();
        numberOfSetInPlayOff.setName(ExtensionName.NUMBER_OF_SET_IN_PLAY_OFF);
        String numberOfSetInPlayOffFromReq = request.getParameter("numberOfSetInPlayOff");
        numberOfSetInPlayOff.setValue(numberOfSetInPlayOffFromReq);

        Extension isRating = new Extension();
        isRating.setName(ExtensionName.IS_RATING);
        String isRatingFromReq = request.getParameter("isRating");
        isRating.setValue(isRatingFromReq);

        Extension numberOfPlayers = new Extension();
        numberOfPlayers.setName(ExtensionName.NUMBER_OF_PLAYERS);
        String numberOfPlayersFromReq = request.getParameter("numberOfPlayers");
        numberOfPlayers.setValue(numberOfPlayersFromReq);

        return new TournamentDto(
                type,
                date,
                List.of(
                        numberOfSetInWorkout,
                        numberOfSetInPlayOff,
                        isRating,
                        numberOfPlayers
                )

        );

    }
}
