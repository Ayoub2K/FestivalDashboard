package com.example.FestivalDashboard.controller;

import com.example.FestivalDashboard.domain.DJ;
import com.example.FestivalDashboard.service.DjService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dj")
@CrossOrigin(origins = "http://localhost:4200")
public class DjController {
    private final DjService djService;

    public DjController(DjService djService) {
        this.djService = djService;
    }


    @PostMapping
    public DJ createDj(@RequestBody DJ dj) throws Exception {
        return djService.saveDj(dj);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DJ> getDjById(@PathVariable long id) {
        DJ dj = djService.getDJById(id);
        return ResponseEntity.ok(dj);
    }

    @GetMapping
    public List<DJ> getAllDjs() {
        return djService.getAllDJs();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DJ> updateDj(@PathVariable long id, @RequestBody DJ updatedDj) throws Exception {
        DJ dj = djService.updateDJ(id, updatedDj);
        return ResponseEntity.ok(dj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDj(@PathVariable long id) throws Exception {
        djService.deleteDJ(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/long")
    public List<DJ> getDjWithLongName() {
        return djService.getDjWithLongName();
    }
}
