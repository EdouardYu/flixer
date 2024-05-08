package web.technologies.flixer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import web.technologies.flixer.dto.AuthenticationDTO;
import web.technologies.flixer.dto.HistoryUserDTO;
import web.technologies.flixer.dto.SignUpDTO;
import web.technologies.flixer.entity.History;
import web.technologies.flixer.entity.Role;
import web.technologies.flixer.entity.User;
import web.technologies.flixer.repository.HistoryRepository;
import web.technologies.flixer.repository.RoleRepository;
import web.technologies.flixer.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final HistoryRepository historyRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, HistoryRepository historyRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.historyRepository = historyRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public boolean isUserExist(AuthenticationDTO authenticationDTO) {
        String emailOrUsername = authenticationDTO.emailOrUsername();
        String password =  authenticationDTO.password();
        User user = userRepository.findUserByEmailOrUsername(emailOrUsername, emailOrUsername).orElseThrow(() -> new RuntimeException("Provided " + emailOrUsername + " not found"));
        String realEncodedUserPassword = user.getPassword();
        // Not encoded yet
        return realEncodedUserPassword.equals(password);
    }

    public ResponseEntity<String> createUser(SignUpDTO signUpDTO) {
        String email = signUpDTO.email();
        String username = signUpDTO.username();
        String password = signUpDTO.password();
        LocalDate birthday = signUpDTO.birthday();
        BigDecimal amount = BigDecimal.valueOf(100);
        Long roleId = signUpDTO.roleId();
        Boolean enabled = false;
        // For now let's save decoded password -> Need to implement encoded password mechanism
        Role role = roleRepository.getRoleById(roleId);
        User user = User.builder()
                .email(email)
                .username(username)
                .password(password)
                .birthday(birthday)
                .amount(amount)
                .role(role)
                .enabled(enabled)
                .build();
        try {
            userRepository.save(user);
            return new ResponseEntity<>("The user : " + user.getUsername() + " has been created", HttpStatus.CREATED);
        } catch (DataAccessException ex) {
            System.out.println(ex + " Error saving user ... ");
            return new ResponseEntity<>("The user : " + user.getUsername() + " has not been created", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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