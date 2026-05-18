package com.cupclash.service;

import com.cupclash.model.Team;
import org.springframework.stereotype.Service;

@Service
public class EloService {

    private static final int    HOST_BOOST = 100;  // applied to USA, Mexico, Canada in home matches
    private static final double DRAW_BASE  = 0.30; // base draw probability when teams are evenly matched

    /**
     * Calculates win/draw/loss probabilities for a match between teamA and teamB.
     * Applies the host boost to whichever team has isHost=true.
     */
    public MatchProbability calculate(Team teamA, Team teamB) {
        double eloA = teamA.getEloRating() + (teamA.isHost() ? HOST_BOOST : 0);
        double eloB = teamB.getEloRating() + (teamB.isHost() ? HOST_BOOST : 0);

        // Standard Elo expected score formula
        double expectedA = 1.0 / (1.0 + Math.pow(10.0, (eloB - eloA) / 400.0));

        // Draw probability peaks at 30% when teams are equal, falls as gap widens
        double draw = DRAW_BASE * (1.0 - Math.abs(2.0 * expectedA - 1.0));
        double winA = expectedA - (draw / 2.0);
        double winB = 1.0 - winA - draw;

        return new MatchProbability(round(winA), round(draw), round(winB));
    }

    private double round(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }

    // ─── Result carrier (Java 17 record — immutable, no boilerplate) ──────────

    public record MatchProbability(double winA, double draw, double winB) {

        public String winAPercent() { return String.format("%.1f%%", winA * 100); }
        public String drawPercent() { return String.format("%.1f%%", draw * 100); }
        public String winBPercent() { return String.format("%.1f%%", winB * 100); }
    }
}
