package com.cupclash.model;

import jakarta.persistence.*;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int eloRating;

    @Column(nullable = false)
    private String confederation;

    // true for USA, Mexico, Canada — triggers the +100 Elo host boost in EloService
    @Column(nullable = false)
    private boolean isHost;

    // ─── Constructors ──────────────────────────────────────────────────────────

    public Team() {}

    public Team(String name, int eloRating, String confederation, boolean isHost) {
        this.name = name;
        this.eloRating = eloRating;
        this.confederation = confederation;
        this.isHost = isHost;
    }

    // ─── Getters & Setters ─────────────────────────────────────────────────────

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getEloRating() { return eloRating; }
    public void setEloRating(int eloRating) { this.eloRating = eloRating; }

    public String getConfederation() { return confederation; }
    public void setConfederation(String confederation) { this.confederation = confederation; }

    public boolean isHost() { return isHost; }
    public void setHost(boolean isHost) { this.isHost = isHost; }

    @Override
    public String toString() {
        return "Team{id=" + id + ", name='" + name + "', elo=" + eloRating + ", host=" + isHost + "}";
    }
}
