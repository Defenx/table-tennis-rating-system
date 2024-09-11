package service.tournament.create;

import dto.TournamentDto;
import entity.Extension;
import enums.ExtensionName;
import enums.Status;
import enums.TournamentType;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TournamentCreateExtractorService implements TournamentCreateExtractor {
    @Override
    public TournamentDto extract(HttpServletRequest req) {
        var extensions = extractExtensions(req);
        var tournamentTypeStr = req.getParameter("type");
        var tournamentDateStr = req.getParameter("date");
        var tournamentType = TournamentType.valueOf(tournamentTypeStr);
        var tournamentDate = LocalDate.parse(tournamentDateStr);
        var defaultStage = Integer.valueOf(0);
        var defaultStatus = Status.NEW;

        return new TournamentDto(
                tournamentType,
                defaultStatus,
                tournamentDate,
                defaultStage,
                extensions
        );
    }

    private List<Extension> extractExtensions(HttpServletRequest req) {
        return Arrays.stream(ExtensionName.values())
                .filter(extensionName -> extensionName != ExtensionName.AVERAGE_RATING)
                .map(extensionName -> extensionName.buildExtension(req.getParameter(extensionName.name().toLowerCase())))
                .toList();
    }
}
