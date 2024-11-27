package com.example.FestivalDashboard.controller;

import com.example.FestivalDashboard.domain.DJ;
import com.example.FestivalDashboard.domain.Performance;
import com.example.FestivalDashboard.service.PerformanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/performance")
@CrossOrigin(origins = "http://localhost:4200")
public class PerformanceController {
    private final PerformanceService performanceService;

    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Performance> getPerformanceById(@PathVariable long id) throws Exception {
        Performance performance = performanceService.getPerformanceById(id);
        return ResponseEntity.ok(performance);
    }

    @GetMapping
    public List<Performance> getAllPerformance() {
        return performanceService.getAllPerformances();
    }

    @PostMapping
    public Performance createPerformance(@RequestBody Performance performance) throws Exception {
        return performanceService.savePerformance(performance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Performance> updatePerformance(@PathVariable long id, @RequestBody Performance updatedPerformance) throws Exception {
        Performance performance = performanceService.updatePerformance(id, updatedPerformance);
        return ResponseEntity.ok(performance);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerformance(@PathVariable long id) throws Exception {
        List<Long> reviewIds = getReviews(id);
        System.out.println(reviewIds);

        if (reviewIds != null && !reviewIds.isEmpty()) {
            RestTemplate restTemplate = new RestTemplate(); // Create instance outside loop
            for (Long reviewId : reviewIds) {
                System.out.println("delete review with id " + reviewId);
                String url = "http://localhost:9191/api/review/" + reviewId; // Use reviewId
                try {
                    restTemplate.delete(url);
                } catch (Exception e) {
                    System.err.println("Failed to delete review with id " + reviewId + ": " + e.getMessage());
                }
            }
        } else {
            System.out.println("No reviews found to delete.");
        }

        performanceService.deletePerformance(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reviews/{id}")
    public List<Long> getReviews(@PathVariable long id) {
        String url = "http://localhost:9191/api/review/performanceId/" + id;
        System.out.println(url);
        RestTemplate restTemplate = new RestTemplate();

        Review[] reviews = restTemplate.getForObject(url, Review[].class);
        List<Long> reviewId = new ArrayList<>();

        if (reviews != null) {
            for (Review review : reviews) {
                reviewId.add(review.getId());
            }
        }

        return reviewId;
    }

    public static class Review {
        private Long id;

        public Long getId() {
            return id;
        }
    }

}
