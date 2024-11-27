package com.example.FestivalDashboard.service;

import com.example.FestivalDashboard.domain.DJ;
import com.example.FestivalDashboard.domain.Performance;
import com.example.FestivalDashboard.exception.DjNotFoundException;
import com.example.FestivalDashboard.repository.DjRepository;
import com.example.FestivalDashboard.repository.PerformanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DjService {

    private final DjRepository djRepository;
    private final PerformanceService performanceService;

    public DjService(DjRepository djRepository, PerformanceService performanceService) {
        this.djRepository = djRepository;
        this.performanceService = performanceService;
    }

    public DJ getDJById(Long id) {
        return djRepository.findDJById(id)
                .orElseThrow(() -> new DjNotFoundException("DJ with id " + id + " not found"));
    }

    public DJ saveDj(DJ dj) {
        djRepository.save(dj);
        return dj;
    }

    public List<DJ> getAllDJs() {
        return djRepository.findAll();
    }

    public DJ updateDJ(long id, DJ updatedDJ) {
        DJ existingDj = djRepository.findDJById(id)
                .orElseThrow(() -> new DjNotFoundException("DJ with id " + id + " not found"));;

        existingDj.setName(updatedDJ.getName());
        existingDj.setAge(updatedDJ.getAge());
        existingDj.setGenre(updatedDJ.getGenre());

        return djRepository.save(existingDj);

    }

    public void deleteDJ(long id) throws Exception {
        DJ existingDj = djRepository.findDJById(id)
                .orElseThrow(() -> new DjNotFoundException("DJ with id " + id + " not found"));;
        List<Performance> performances = performanceService.getPerformancesByDjId(id);
        for (Performance performance : performances) {
            performanceService.deletePerformance(performance.getId());
        }
        djRepository.delete(existingDj);
    }

    public List<DJ> getDjWithLongName() {
        return djRepository.findDjsByNameWithNameLongerThanSixCharachters();
    }
}
