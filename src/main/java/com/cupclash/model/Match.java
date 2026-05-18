package com.cupclash.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many matches can involve the same team, but each match has exactly one teamA
    @ManyToOne(optional = false)
    @JoinColumn(name = "team_a_id")
    private Team teamA;

    @ManyToOne(optional = false)
    @JoinColumn(name = "team_b_id")
    private Team teamB;

    @Column(nullable = false)
    private String stage; // "GROUP", "ROUND_OF_32", "ROUND_OF_16", "QUARTER_FINAL", "SEMI_FINAL", "FINAL"

    @Column
    private String groupName; // "A" through "L" for group stage matches; null for knockouts

    @Column
    private LocalDateTime matchDate;

    // ─── Constructors ──────────────────────────────────────────────────────────

    public Match() {}

    public Match(Team teamA, Team teamB, String stage, String groupName, LocalDateTime matchDate) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.stage = stage;
        this.groupName = groupName;
        this.matchDate = matchDate;
    }

    // ─── Getters & Setters ─────────────────────────────────────────────────────

    public Long getId() { return id; }

    public Team getTeamA() { return teamA; }
    public void setTeamA(Team teamA) { this.teamA = teamA; }

    public Team getTeamB() { return teamB; }
    public void setTeamB(Team teamB) { this.teamB = teamB; }

    public String getStage() { return stage; }
    public void setStage(String stage) { this.stage = stage; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public LocalDateTime getMatchDate() { return matchDate; }
    public void setMatchDate(LocalDateTime matchDate) { this.matchDate = matchDate; }

    @Override
    public String toString() {
        return "Match{id=" + id + ", " + teamA.getName() + " vs " + teamB.getName()
                + ", stage=" + stage + ", group=" + groupName + "}";
    }
}
