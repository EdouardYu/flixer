package web.technologies.flixer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import web.technologies.flixer.dto.AuthenticationDTO;
import web.technologies.flixer.dto.SignUpDTO;
import web.technologies.flixer.entity.Role;
import web.technologies.flixer.entity.User;
import web.technologies.flixer.repository.RoleRepository;
import web.technologies.flixer.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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

    public boolean isUserCreated(SignUpDTO signUpDTO) {
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
            return true;
        } catch (DataAccessException ex) {
            System.out.println(ex + " Error saving user ... ");
        }
        System.out.println("Error saving user ... ");
        return false;
    }
}