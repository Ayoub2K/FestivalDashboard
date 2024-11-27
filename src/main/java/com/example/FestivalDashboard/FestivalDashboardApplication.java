package com.example.FestivalDashboard;

import com.example.FestivalDashboard.domain.DJ;
import com.example.FestivalDashboard.domain.Performance;
import com.example.FestivalDashboard.repository.DjRepository;
import com.example.FestivalDashboard.repository.PerformanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class FestivalDashboardApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(FestivalDashboardApplication.class);

	private final DjRepository djRepository;
	private final PerformanceRepository performanceRepository;

	public FestivalDashboardApplication(DjRepository djRepository, PerformanceRepository performanceRepository) {
		this.djRepository = djRepository;
		this.performanceRepository = performanceRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(FestivalDashboardApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (djRepository.count() == 0) {
			DJ dj1 = djRepository.save(new DJ("Martin Garrix", "Classical", 24));
			DJ dj2 = djRepository.save(new DJ("Hardwell", "Hip-Hop", 23));
			DJ dj3 = djRepository.save(new DJ("David Guetta", "Funk", 23));
			DJ dj4 = djRepository.save(new DJ("Afrojack", "Techno", 23));
			DJ dj5 = djRepository.save(new DJ("Don Diablo", "Funk", 23));
		} else {
			logger.info("DJs already exist in the database.");
		}

		if (performanceRepository.count() == 0) {
			performanceRepository.save(new Performance("Into the Woods", new ArrayList<>(List.of("Terug In De Tijd", "Die With A Smile", "The Sound Of Silence")), LocalDate.now().plusDays(2), LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(2), djRepository.findByName("Martin Garrix").orElseThrow()));
			performanceRepository.save(new Performance("A Day at the Park", new ArrayList<>(List.of("A bar song", "Move", "Carry you home")), LocalDate.now().plusDays(4), LocalDateTime.now().plusDays(4), LocalDateTime.now().plusDays(2).plusHours(3), djRepository.findByName("Hardwell").orElseThrow()));
			performanceRepository.save(new Performance("Titanium Festival", new ArrayList<>(List.of("I had some help", "Espresso", "The Sound Of Silence")), LocalDate.now().plusDays(7), LocalDateTime.now().plusDays(7), LocalDateTime.now().plusDays(2).plusHours(3), djRepository.findByName("David Guetta").orElseThrow()));
			performanceRepository.save(new Performance("A Day at the Park 2", new ArrayList<>(List.of("A bar song", "Move", "Carry you home")), LocalDate.now().plusDays(7), LocalDateTime.now().plusDays(7), LocalDateTime.now().plusDays(2).plusHours(4), djRepository.findByName("Afrojack").orElseThrow()));
			performanceRepository.save(new Performance("Festival of a lifetime", new ArrayList<>(List.of("I had some help", "Espresso", "The Sound Of Silence")), LocalDate.now().plusDays(10), LocalDateTime.now().plusDays(10), LocalDateTime.now().plusDays(2).plusHours(5), djRepository.findByName("Don Diablo").orElseThrow()));
		} else {
			logger.info("Performances already exist in the database.");
		}

	}
}
