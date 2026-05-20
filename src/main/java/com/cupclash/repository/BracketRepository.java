package com.cupclash.repository;

import com.cupclash.model.Bracket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BracketRepository extends JpaRepository<Bracket, Long> {

    // Retrieves the most recently saved bracket (there is only ever one active bracket)
    Optional<Bracket> findTopByOrderByUpdatedAtDesc();
}
