package web.technologies.flixer.controller;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.technologies.flixer.service.HistoryService;


@AllArgsConstructor
@RestController
@RequestMapping("/history")
@CrossOrigin(origins = "http://localhost:5173")
public class HistoryController {
    private final HistoryService historyService;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addHistory(@RequestBody Long userId, @RequestBody Long movieId) {
        System.out.println("userId: " + userId + " movieId: " + movieId);
        return this.historyService.saveHistory(userId, movieId);
    }

}
