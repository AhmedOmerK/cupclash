package com.cupclash.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "predictions")
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Each prediction belongs to exactly one match
    @ManyToOne(optional = false)
    @JoinColumn(name = "match_id")
    private Match match;

    // What the user predicted: "TEAM_A", "DRAW", or "TEAM_B"
    @Column(nullable = false)
    private String predictedOutcome;

    // The name of the team the user expects to win (null if draw predicted)
    @Column
    private String predictedWinner;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Auto-set the timestamp before the record is first saved
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ─── Constructors ──────────────────────────────────────────────────────────

    public Prediction() {}

    public Prediction(Match match, String predictedOutcome, String predictedWinner) {
        this.match = match;
        this.predictedOutcome = predictedOutcome;
        this.predictedWinner = predictedWinner;
    }

    // ─── Getters & Setters ─────────────────────────────────────────────────────

    public Long getId() { return id; }

    public Match getMatch() { return match; }
    public void setMatch(Match match) { this.match = match; }

    public String getPredictedOutcome() { return predictedOutcome; }
    public void setPredictedOutcome(String predictedOutcome) { this.predictedOutcome = predictedOutcome; }

    public String getPredictedWinner() { return predictedWinner; }
    public void setPredictedWinner(String predictedWinner) { this.predictedWinner = predictedWinner; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "Prediction{id=" + id + ", match=" + match.getId()
                + ", outcome='" + predictedOutcome + "', winner='" + predictedWinner + "'}";
    }
}
