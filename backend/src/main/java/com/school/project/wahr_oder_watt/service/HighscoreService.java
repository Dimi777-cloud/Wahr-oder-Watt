package com.school.project.wahr_oder_watt.service;

import com.school.project.wahr_oder_watt.model.Highscore;
import com.school.project.wahr_oder_watt.repository.HighscoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service zur Verwaltung von Highscores.
 */
@Service
@RequiredArgsConstructor
public class HighscoreService {

  private final HighscoreRepository highscoreRepository;

  /**
   * Gibt alle Highscores zurück.
   *
   * @return Liste aller Highscores.
   */
  public List<Highscore> findAll() {
    return highscoreRepository.findAll();
  }
  /**
   * Gibt alle Highscores sortiert nach Punktzahl in absteigender Reihenfolge zurück.
   *
   * @return Liste aller Highscores, sortiert nach Punktzahl (absteigend).
   */
  public List<Highscore> findAllSortedByScoreDesc() {
    return highscoreRepository.findAllByOrderByScoreDesc();
  }

  /**
   * Sucht einen Highscore anhand der ID.
   *
   * @param id ID des gesuchten Highscores.
   * @return Highscore mit der angegebenen ID.
   * @throws RuntimeException falls der Highscore nicht gefunden wird.
   */
  public Highscore findById(Long id) {
    return highscoreRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Highscore nicht gefunden mit ID: " + id));
  }

  /**
   * Speichert einen neuen Highscore.
   *
   * @param highscore Der zu speichernde Highscore.
   * @return Der gespeicherte Highscore.
   */
  public Highscore save(Highscore highscore) {
    return highscoreRepository.save(highscore);
  }

  /**
   * Aktualisiert einen bestehenden Highscore.
   *
   * @param id ID des zu aktualisierenden Highscores.
   * @param highscore Neue Highscore-Daten.
   * @return Der aktualisierte Highscore.
   * @throws RuntimeException falls der Highscore nicht gefunden wird.
   */
  public Highscore update(Long id, Highscore highscore) {
    Highscore existingHighscore = findById(id);
    existingHighscore.setScore(highscore.getScore());
    existingHighscore.setPlayers(highscore.getPlayers());
    return highscoreRepository.save(existingHighscore);
  }

  /**
   * Löscht einen Highscore anhand der ID.
   *
   * @param id ID des zu löschenden Highscores.
   */
  public void delete(Long id) {
    highscoreRepository.deleteById(id);
  }
}