package dto;

import entity.Extension;
import enums.Status;
import enums.TournamentType;

import java.time.LocalDate;
import java.util.List;

public record TournamentDto(
        TournamentType type,
        Status status,
        LocalDate date,
        Integer stage,
        List<Extension> extensions
) {
}
