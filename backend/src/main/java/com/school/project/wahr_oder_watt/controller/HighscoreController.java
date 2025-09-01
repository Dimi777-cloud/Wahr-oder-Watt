package com.school.project.wahr_oder_watt.controller;

import com.school.project.wahr_oder_watt.model.Highscore;
import com.school.project.wahr_oder_watt.service.HighscoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-Controller zur Verwaltung von Highscores.
 */
@RestController
@RequestMapping("/api/highscores")
@RequiredArgsConstructor
public class HighscoreController {

  private final HighscoreService highscoreService;

  /**
   * Gibt alle Highscores zurück.
   */
  @GetMapping
  public ResponseEntity<List<Highscore>> getAllHighscores() {
    return ResponseEntity.ok(highscoreService.findAllSortedByScoreDesc());
  }

  /**
   * Gibt einen Highscore anhand der ID zurück.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Highscore> getHighscore(@PathVariable Long id) {
    Highscore highscore = highscoreService.findById(id);
    return ResponseEntity.ok(highscore);
  }

  /**
   * Erstellt einen neuen Highscore.
   */
  @PostMapping
  public ResponseEntity<Highscore> createHighscore(@RequestBody Highscore highscore) {
    Highscore created = highscoreService.save(highscore);
    return ResponseEntity.ok(created);
  }

  /**
   * Aktualisiert einen bestehenden Highscore.
   */
  @PutMapping("/{id}")
  public ResponseEntity<Highscore> updateHighscore(@PathVariable Long id, @RequestBody Highscore highscore) {
    Highscore updated = highscoreService.update(id, highscore);
    return ResponseEntity.ok(updated);
  }

  /**
   * Löscht einen Highscore anhand der ID.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteHighscore(@PathVariable Long id) {
    highscoreService.delete(id);
    return ResponseEntity.noContent().build();
  }
}