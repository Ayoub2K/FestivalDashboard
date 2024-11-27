package com.example.FestivalDashboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "performances")
public class Performance implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Setter
    private Long ExternalDJId;

    @Setter
    private String name;

    @Setter
    @ElementCollection
    private List<String> songs;

    @Setter
    private LocalDate dayOfPerformance;
    @Setter
    private LocalDateTime startTime;
    @Setter
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dj_id", nullable = false)
    @JsonIgnoreProperties({"performances"})  // Prevents circular reference issues
    private DJ dj;
    

    public Performance() {

    }

    public Performance(String name, List<String> songs, LocalDate dayOfPerformance, LocalDateTime startTime, LocalDateTime endTime, DJ dj) {
        this.name = name;
        this.songs = songs;
        this.dayOfPerformance = dayOfPerformance;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dj = dj;
    }

    public Performance(Long externalDJId, String name, List<String> songs, LocalDate dayOfPerformance, LocalDateTime startTime, LocalDateTime endTime, DJ dj) {
        ExternalDJId = externalDJId;
        this.name = name;
        this.songs = songs;
        this.dayOfPerformance = dayOfPerformance;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dj = dj;
    }
}
