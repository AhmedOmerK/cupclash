package com.cupclash.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "brackets")
public class Bracket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The entire bracket state stored as a JSON string.
    // Using TEXT so it can hold the full map of all 31 knockout match predictions.
    @Column(columnDefinition = "TEXT", nullable = false)
    private String bracketJson;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ─── Constructors ──────────────────────────────────────────────────────────

    public Bracket() {}

    public Bracket(String bracketJson) {
        this.bracketJson = bracketJson;
    }

    // ─── Getters & Setters ─────────────────────────────────────────────────────

    public Long getId() { return id; }

    public String getBracketJson() { return bracketJson; }
    public void setBracketJson(String bracketJson) { this.bracketJson = bracketJson; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
