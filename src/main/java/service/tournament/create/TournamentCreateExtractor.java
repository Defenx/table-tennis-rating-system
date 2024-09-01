package service.tournament.create;

import dto.TournamentDto;
import jakarta.servlet.http.HttpServletRequest;

public interface TournamentCreateExtractor {
    TournamentDto extract(HttpServletRequest request);
}
