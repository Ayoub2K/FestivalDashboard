package com.example.FestivalDashboard.service;

import com.example.FestivalDashboard.domain.DJ;
import com.example.FestivalDashboard.domain.Performance;
import com.example.FestivalDashboard.exception.PerformanceNotFoundException;
import com.example.FestivalDashboard.repository.DjRepository;
import com.example.FestivalDashboard.repository.PerformanceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final DjRepository djRepository;

    public PerformanceService(PerformanceRepository performanceRepository, DjRepository djRepository) {
        this.performanceRepository = performanceRepository;
        this.djRepository = djRepository;
    }

    public Performance getPerformanceById(Long id) {
        return performanceRepository.findPerformanceById(id)
                .orElseThrow(() -> new PerformanceNotFoundException("Performance with id " + id + " not found"));
    }

    public List<Performance> getAllPerformances() {
        return performanceRepository.findAll();
    }

    public List<Performance> getPerformancesByDjId(long id) {
        return performanceRepository.findByDjId(id);
    }

    public void deletePerformance(Long id) {
        performanceRepository.delete(getPerformanceById(id));
    }

    public Performance updatePerformance(long id, Performance updatedPerformance) {
        Performance existingPerformance = performanceRepository.findPerformanceById(id)
                .orElseThrow(() -> new PerformanceNotFoundException("Performance with id " + id + " not found"));

        existingPerformance.setName(updatedPerformance.getName());
        existingPerformance.setSongs(updatedPerformance.getSongs());
        existingPerformance.setDayOfPerformance(updatedPerformance.getDayOfPerformance());
        existingPerformance.setStartTime(updatedPerformance.getStartTime());
        existingPerformance.setEndTime(updatedPerformance.getEndTime());

        return performanceRepository.save(existingPerformance);
    }

    public Performance savePerformance(Performance performance) {
        // Fetch the DJ using externalDJId
        Optional<DJ> djOptional = djRepository.findDJById(performance.getExternalDJId());

        // Check if DJ is present, and set it on the performance
        if (djOptional.isPresent()) {
            DJ dj = djOptional.get();
            performance.setDj(dj);  // Assuming Performance class has a 'DJ' field and setter method
        } else {
            // Handle the case where DJ is not found
            throw new EntityNotFoundException("DJ with ID " + performance.getExternalDJId() + " not found.");
        }

        // Save the performance and return the saved entity
        return performanceRepository.save(performance);
    }
}
