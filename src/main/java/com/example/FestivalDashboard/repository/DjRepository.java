package com.example.FestivalDashboard.repository;

import com.example.FestivalDashboard.domain.DJ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DjRepository extends JpaRepository<DJ, Long> {
    Optional<DJ> findDJById(Long id);

    Optional<DJ> findByName(String name);

    @Query("SELECT d from DJ d WHERE length(d.name) > 6")
    List<DJ> findDjsByNameWithNameLongerThanSixCharachters();
}
