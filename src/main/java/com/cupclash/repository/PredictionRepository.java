package com.cupclash.repository;

import com.cupclash.model.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {

    // Get all predictions for a specific match
    List<Prediction> findByMatchId(Long matchId);

    // Check if the user already predicted a specific match (for duplicate prevention)
    Optional<Prediction> findFirstByMatchId(Long matchId);
}
