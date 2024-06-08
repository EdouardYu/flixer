package web.technologies.flixer.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import web.technologies.flixer.entity.History;
import web.technologies.flixer.entity.HistoryId;
import web.technologies.flixer.entity.Movie;
import web.technologies.flixer.entity.User;
import web.technologies.flixer.repository.MovieRepository;
import web.technologies.flixer.repository.UserRepository;

import java.time.Instant;

@AllArgsConstructor
@Service
public class HistoryService {
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public ResponseEntity<String> saveHistory(Long userId, Long movieId) {
        Instant watchedAt = Instant.now();
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // Retrieve the User entity
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create the History instance
        History history = new History();
        history.setId(new HistoryId(movieId, userId));
        history.setMovie(movie);
        history.setUser(user);
        history.setWatched_at(watchedAt);

        // Save the History instance
        System.out.println("History: " + history);
//        this.historyRepository.save(history);
        return new ResponseEntity<>("History added for user id " + userId.toString() + " for the movie " + movieId.toString(), HttpStatus.CREATED);
    }
}
