package com.cupclash.repository;

import com.cupclash.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    // Find all matches in a specific group (e.g. "A", "B")
    List<Match> findByGroupName(String groupName);

    // Find all matches in a specific stage (e.g. "GROUP", "FINAL")
    List<Match> findByStage(String stage);
}
