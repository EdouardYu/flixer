package web.technologies.flixer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import web.technologies.flixer.repository.HistoryRepository;

@AllArgsConstructor
@Service
public class HistoryService {
    private final HistoryRepository historyRepository;
}
