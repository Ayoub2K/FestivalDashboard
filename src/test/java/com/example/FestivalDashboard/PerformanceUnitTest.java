package com.example.FestivalDashboard;

import com.example.FestivalDashboard.domain.DJ;
import com.example.FestivalDashboard.domain.Performance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PerformanceUnitTest {

    private Performance performance;

    @Mock
    private DJ dj;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Setting up the mock DJ object
        when(dj.getName()).thenReturn("DJ Mock");
    }

    @Test
    public void testPerformanceConstructor() {
        // Given
        String name = "Performance 1";
        List<String> songs = Arrays.asList("Song 1", "Song 2", "Song 3");
        LocalDate dayOfPerformance = LocalDate.now();
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(2);

        // When
        Performance performance = new Performance(name, songs, dayOfPerformance, startTime, endTime, dj);

        // Then
        assertEquals(name, performance.getName());
        assertEquals(songs, performance.getSongs());
        assertEquals(dayOfPerformance, performance.getDayOfPerformance());
        assertEquals(startTime, performance.getStartTime());
        assertEquals(endTime, performance.getEndTime());
        assertEquals(dj, performance.getDj());
    }

    @Test
    public void testPerformanceSettersAndGetters() {
        // Given
        performance = new Performance();
        String name = "Performance 2";
        List<String> songs = Arrays.asList("Track 1", "Track 2");
        LocalDate dayOfPerformance = LocalDate.now().plusDays(1);
        LocalDateTime startTime = LocalDateTime.now().plusDays(1).plusHours(2);
        LocalDateTime endTime = startTime.plusHours(3);

        // When
        performance.setName(name);
        performance.setSongs(songs);
        performance.setDayOfPerformance(dayOfPerformance);
        performance.setStartTime(startTime);
        performance.setEndTime(endTime);
        performance.setDj(dj);

        // Then
        assertEquals(name, performance.getName());
        assertEquals(songs, performance.getSongs());
        assertEquals(dayOfPerformance, performance.getDayOfPerformance());
        assertEquals(startTime, performance.getStartTime());
        assertEquals(endTime, performance.getEndTime());
        assertEquals(dj, performance.getDj());
    }

    @Test
    public void testNoArgsConstructor() {
        // Given
        performance = new Performance();

        // Then
        assertNull(performance.getName());
        assertNull(performance.getSongs());
        assertNull(performance.getDayOfPerformance());
        assertNull(performance.getStartTime());
        assertNull(performance.getEndTime());
        assertNull(performance.getDj());
    }

    @Test
    public void testSettingDJ() {
        // Given
        performance = new Performance();
        DJ newDJ = mock(DJ.class);
        when(newDJ.getName()).thenReturn("DJ New");

        // When
        performance.setDj(newDJ);

        // Then
        assertEquals(newDJ, performance.getDj());
        assertEquals("DJ New", performance.getDj().getName());
    }
}