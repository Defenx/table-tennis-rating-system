package service.tournament.match.strategy;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import service.tournament.round.RoundService;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UpdateExistingRoundScoreStrategy implements ScoreProcessingStrategy {
    private final RoundService roundService;

    @Override
    public void processScores(UUID matchId, HttpServletRequest req) {
        String formId = req.getParameter("formId");
        if (formId == null) {
            throw new IllegalArgumentException("formId is missing");
        }

        Map<String, String[]> parameterMap = req.getParameterMap();

        Optional<Map.Entry<String, String[]>> optionalEntry = parameterMap.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("roundScore1_"))
                .findFirst();

        optionalEntry.ifPresent(entry -> {
            String paramName = entry.getKey();
            String roundIndexStr = paramName.substring("roundScore1_".length());
            String[] roundScore1Params = entry.getValue();
            String[] roundScore2Params = parameterMap.get("roundScore2_" + roundIndexStr);

            if (roundScore1Params != null && roundScore2Params != null && roundScore1Params.length > 0 && roundScore2Params.length > 0) {
                Integer roundScore1 = Integer.parseInt(roundScore1Params[0]);
                Integer roundScore2 = Integer.parseInt(roundScore2Params[0]);
                int roundIndex = Integer.parseInt(roundIndexStr);
                if (formId.equals("form_" + roundIndex)) {
                    roundService.updateRoundScores(matchId, roundIndex + 1, roundScore1, roundScore2);
                }
            }
        });
    }
}
