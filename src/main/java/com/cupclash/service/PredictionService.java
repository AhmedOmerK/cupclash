package com.cupclash.service;

import com.cupclash.model.Match;
import com.cupclash.model.Prediction;
import com.cupclash.repository.MatchRepository;
import com.cupclash.repository.PredictionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredictionService {

    private final PredictionRepository predictionRepository;
    private final MatchRepository matchRepository;

    public PredictionService(PredictionRepository predictionRepository,
                             MatchRepository matchRepository) {
        this.predictionRepository = predictionRepository;
        this.matchRepository = matchRepository;
    }

    /**
     * Saves a prediction for a given match.
     * predictedOutcome must be "TEAM_A", "DRAW", or "TEAM_B".
     * Returns the saved prediction.
     * Throws if the match doesn't exist or the outcome value is invalid.
     */
    public Prediction savePrediction(long matchId, String predictedOutcome) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Match not found: " + matchId));

        if (!isValidOutcome(predictedOutcome)) {
            throw new IllegalArgumentException("Invalid outcome: " + predictedOutcome);
        }

        String predictedWinner = resolveWinner(match, predictedOutcome);

        Prediction prediction = new Prediction(match, predictedOutcome, predictedWinner);
        return predictionRepository.save(prediction);
    }

    // Returns all predictions ever saved
    public List<Prediction> getAllPredictions() {
        return predictionRepository.findAll();
    }

    // Returns all predictions for a specific match
    public List<Prediction> getPredictionsForMatch(Long matchId) {
        return predictionRepository.findByMatchId(matchId);
    }

    // Returns true if the user has already predicted this match
    public boolean hasPrediction(Long matchId) {
        return predictionRepository.findFirstByMatchId(matchId).isPresent();
    }

    // ─── Private helpers ──────────────────────────────────────────────────────

    private boolean isValidOutcome(String outcome) {
        return outcome != null &&
               (outcome.equals("TEAM_A") || outcome.equals("DRAW") || outcome.equals("TEAM_B"));
    }

    private String resolveWinner(Match match, String outcome) {
        return switch (outcome) {
            case "TEAM_A" -> match.getTeamA().getName();
            case "TEAM_B" -> match.getTeamB().getName();
            default       -> null; // draw has no winner
        };
    }
}
