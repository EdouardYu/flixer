package web.technologies.flixer.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import web.technologies.flixer.entity.History;
import web.technologies.flixer.repository.HistoryRepository;

import java.time.Instant;

@AllArgsConstructor
@Service
public class HistoryService {
    private final HistoryRepository historyRepository;

    public ResponseEntity<String> saveHistory(Long userId, Long movieId) {
        Instant watchedAt = Instant.now();
        History history = new History(movieId, userId, watchedAt);
        this.historyRepository.save(history);
        return new ResponseEntity<>("History added for user id " + userId.toString() + " for the movie " + movieId.toString(), HttpStatus.CREATED);
    }
}
