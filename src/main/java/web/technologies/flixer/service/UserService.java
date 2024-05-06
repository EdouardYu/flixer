package web.technologies.flixer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.technologies.flixer.dto.AuthenticationDTO;
import web.technologies.flixer.entity.User;
import web.technologies.flixer.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public boolean isUserExist(AuthenticationDTO authenticationDTO) {
        String email = authenticationDTO.email();
        String password =  authenticationDTO.password();
        User user =  userRepository.findUserByEmail(email).orElseThrow(() -> new RuntimeException("Email" + email + "not found"));
        String realEncodedUserPassword = user.getPassword();
        // Not encoded yet
        return realEncodedUserPassword.equals(password);
    }
}
