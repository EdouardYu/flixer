package web.technologies.flixer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import web.technologies.flixer.dto.HistoryUserDTO;
import web.technologies.flixer.entity.History;
import web.technologies.flixer.entity.User;
import web.technologies.flixer.repository.HistoryRepository;
import web.technologies.flixer.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final HistoryRepository historyRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findUserById(id).orElseThrow(
                () -> new RuntimeException("No user exist with the id " + id.toString()
                ));
    }

    public List<HistoryUserDTO> getHistoryByUserId(Long id) {
         List<History> histories = historyRepository.findByUserId(id);
         List<HistoryUserDTO> historiesUser = new ArrayList<>();
         for (History history: histories) {
             historiesUser.add(new HistoryUserDTO(history.getMovie(), history.getWatched_at()));
         }
         return historiesUser;
     }

}