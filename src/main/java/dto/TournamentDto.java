package dto;

import entity.Extension;
import enums.TournamentType;

import java.time.LocalDate;
import java.util.List;

public record TournamentDto(
        TournamentType type,
        LocalDate data,
        List<Extension> extensions
) {
}
