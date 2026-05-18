package com.cupclash.repository;

import com.cupclash.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    // JpaRepository provides: save(), findAll(), findById(), count(), deleteAll(), etc.
    // No extra code needed for basic CRUD.
}