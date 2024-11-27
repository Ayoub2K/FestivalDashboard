package com.example.FestivalDashboard;

import com.example.FestivalDashboard.domain.DJ;
import com.example.FestivalDashboard.domain.Performance;
import com.example.FestivalDashboard.exception.DjNotFoundException;
import com.example.FestivalDashboard.repository.DjRepository;
import com.example.FestivalDashboard.service.DjService;
import com.example.FestivalDashboard.service.PerformanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DjServiceIntegrationTest {

    @InjectMocks
    private DjService djService;

    @Mock
    private DjRepository djRepository;

    @Mock
    private PerformanceService performanceService;

    private DJ sampleDj;
    private DJ updatedDj;
    private Performance samplePerformance;

    @BeforeEach
    public void setUp() {
        sampleDj = new DJ();
        sampleDj.setId(1L);
        sampleDj.setName("Sample DJ");
        sampleDj.setAge(30);
        sampleDj.setGenre("House");

        samplePerformance = new Performance();
        samplePerformance.setId(1L);
        samplePerformance.setName("Sample Performance");
        samplePerformance.setDj(sampleDj);

        sampleDj.setPerformances(Arrays.asList(samplePerformance));

        updatedDj = new DJ();
        updatedDj.setId(1L);
        updatedDj.setName("Updated DJ");
        updatedDj.setAge(35);
        updatedDj.setGenre("Techno");
    }

    @Test
    public void testSaveDj() {
        when(djRepository.save(sampleDj)).thenReturn(sampleDj);

        DJ savedDj = djService.saveDj(sampleDj);

        assertNotNull(savedDj);
        assertEquals("Sample DJ", savedDj.getName());
        verify(djRepository, times(1)).save(sampleDj);
    }

    @Test
    public void testGetDjById() {
        when(djRepository.findDJById(1L)).thenReturn(Optional.of(sampleDj));

        DJ foundDj = djService.getDJById(1L);

        assertNotNull(foundDj);
        assertEquals(1L, foundDj.getId());
        assertEquals("Sample DJ", foundDj.getName());
    }

    @Test
    public void testGetDjById_NotFound() {
        when(djRepository.findDJById(1L)).thenReturn(Optional.empty());
        assertThrows(DjNotFoundException.class, () -> djService.getDJById(1L));
    }

    @Test
    public void testUpdateDj() {
        when(djRepository.findDJById(1L)).thenReturn(Optional.of(sampleDj));
        when(djRepository.save(sampleDj)).thenReturn(updatedDj);

        DJ updated = djService.updateDJ(1L, updatedDj);

        assertNotNull(updated);
        assertEquals("Updated DJ", updated.getName());
        verify(djRepository, times(1)).save(sampleDj);
        assertEquals(35, sampleDj.getAge()); // Ensure sampleDj has been updated
    }

    @Test
    public void testDeleteDj() throws Exception {
        when(djRepository.findDJById(1L)).thenReturn(Optional.of(sampleDj));
        when(performanceService.getPerformancesByDjId(1L)).thenReturn(Arrays.asList(samplePerformance));

        djService.deleteDJ(1L);

        verify(djRepository, times(1)).delete(sampleDj);
        verify(performanceService, times(1)).deletePerformance(samplePerformance.getId());
    }

    @Test
    public void testGetAllDjs() {
        when(djRepository.findAll()).thenReturn(Arrays.asList(sampleDj));

        List<DJ> djs = djService.getAllDJs();

        assertNotNull(djs);
        assertFalse(djs.isEmpty());
        assertEquals(1, djs.size());
        verify(djRepository, times(1)).findAll();
    }

    @Test
    public void testGetDjsWithLongNames() {
        DJ djWithLongName1 = new DJ();
        djWithLongName1.setId(2L);
        djWithLongName1.setName("DJ Rockstar");
        djWithLongName1.setAge(28);
        djWithLongName1.setGenre("Techno");

        DJ djWithLongName2 = new DJ();
        djWithLongName2.setId(3L);
        djWithLongName2.setName("DJ Superstar");
        djWithLongName2.setAge(33);
        djWithLongName2.setGenre("House");

        DJ djWithShortName = new DJ();
        djWithShortName.setId(4L);
        djWithShortName.setName("DJ Joe");
        djWithShortName.setAge(40);
        djWithShortName.setGenre("Hip-Hop");

        when(djRepository.findDjsByNameWithNameLongerThanSixCharachters())
                .thenReturn(Arrays.asList(djWithLongName1, djWithLongName2));

        List<DJ> result = djService.getDjWithLongName();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertTrue(result.stream().allMatch(dj -> dj.getName().length() > 6));

        assertEquals("DJ Rockstar", result.get(0).getName());
        assertEquals("DJ Superstar", result.get(1).getName());

        verify(djRepository, times(1)).findDjsByNameWithNameLongerThanSixCharachters();
    }
}
