package web.technologies.flixer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.technologies.flixer.repository.HistoryRepository;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;

    @Autowired
    HistoryService(HistoryRepository historyRepository){
        this.historyRepository = historyRepository;
    }
}
