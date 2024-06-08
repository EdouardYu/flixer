package web.technologies.flixer.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.technologies.flixer.service.HistoryService;

@AllArgsConstructor
@RestController
@RequestMapping("/history")
@CrossOrigin(origins = "http://localhost:5173")
public class HistoryController {
    private final HistoryService historyService;

    @PostMapping()
    public ResponseEntity<String> addHistory(@RequestParam Long userId, Long movieId) {
        return this.historyService.saveHistory(userId, movieId);
    }
}
