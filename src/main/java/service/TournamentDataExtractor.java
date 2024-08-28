package service;

import entity.Extension;
import entity.Tournament;
import enums.ExtensionName;
import enums.Status;
import enums.TournamentType;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TournamentDataExtractor {
    public Tournament extractTournament(HttpServletRequest req) {
        var extensions = extractExtensions(req);
        var tournamentTypeStr = req.getParameter("type");
        var tournamentDateStr = req.getParameter("date");
        var tournamentType = TournamentType.valueOf(tournamentTypeStr);
        var tournamentDate = LocalDate.parse(tournamentDateStr);
        var defaultStage = 0;
        var defaultStatus = Status.NEW;

        return Tournament.builder()
                .type(tournamentType)
                .date(tournamentDate)
                .extensions(extensions)
                .stage(defaultStage)
                .status(defaultStatus)
                .build();
    }

    private List<Extension> extractExtensions(HttpServletRequest req) {
        var extensions = new ArrayList<Extension>();

        for (var extensionName : ExtensionName.values()) {
            var extensionValue = req.getParameter(String.valueOf(extensionName));
            if (extensionValue != null) {
                var extension = Extension.builder().name(extensionName).value(extensionValue).build();
                extensions.add(extension);
            }
        }

        return extensions;
    }
}
