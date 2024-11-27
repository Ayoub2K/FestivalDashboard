package com.example.FestivalDashboard.service;

import com.example.FestivalDashboard.domain.DJ;
import com.example.FestivalDashboard.domain.Performance;
import com.example.FestivalDashboard.exception.PerformanceNotFoundException;
import com.example.FestivalDashboard.repository.DjRepository;
import com.example.FestivalDashboard.repository.PerformanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class PerformanceServiceTest {

    @Autowired
    private PerformanceService performanceService;

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private DjRepository djRepository;  // Inject DJRepository

    private DJ testDJ;
    private Performance testPerformance;

    @BeforeEach
    public void setUp() {
        // Set up and save the test DJ entity first
        testDJ = new DJ();
        testDJ.setName("DJ Test");

        // Save the DJ to the database first
        djRepository.save(testDJ);  // Use the injected DJRepository

        // Set up the test Performance entity
        testPerformance = new Performance();
        testPerformance.setName("Test Performance");
        testPerformance.setSongs(List.of("Song 1", "Song 2"));
        testPerformance.setDayOfPerformance(LocalDate.now());
        testPerformance.setStartTime(LocalDateTime.now());
        testPerformance.setEndTime(LocalDateTime.now().plusHours(1));
        testPerformance.setDj(testDJ);  // Associate with the saved DJ

        // Now save the Performance to the repository
        performanceRepository.save(testPerformance);
    }

    @Test
    public void testGetPerformanceById() {
        Performance performance = performanceService.getPerformanceById(testPerformance.getId());
        assertThat(performance).isNotNull();
        assertThat(performance.getName()).isEqualTo("Test Performance");
    }

    @Test
    public void testGetPerformanceByIdNotFound() {
        Long invalidId = -1L;
        assertThrows(PerformanceNotFoundException.class, () -> performanceService.getPerformanceById(invalidId));
    }

    @Test
    public void testGetAllPerformances() {
        List<Performance> performances = performanceService.getAllPerformances();
        assertThat(performances).isNotEmpty();
        assertThat(performances.get(0).getName()).isEqualTo("Into the Woods");
    }


    @Test
    public void testDeletePerformance() {
        // Act: delete the performance
        performanceService.deletePerformance(testPerformance.getId());

        // Assert: verify it is deleted
        Optional<Performance> deletedPerformance = performanceRepository.findPerformanceById(testPerformance.getId());
        assertThat(deletedPerformance).isEmpty();
    }
}
