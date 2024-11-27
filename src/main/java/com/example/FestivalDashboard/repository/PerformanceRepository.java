package com.example.FestivalDashboard.repository;

import com.example.FestivalDashboard.domain.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    Optional<Performance> findPerformanceById(Long id);
    List<Performance> findByDjId(Long id);
}
