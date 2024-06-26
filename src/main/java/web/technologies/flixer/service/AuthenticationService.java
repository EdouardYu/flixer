package web.technologies.flixer.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.technologies.flixer.dto.ActivationDTO;
import web.technologies.flixer.dto.EmailDTO;
import web.technologies.flixer.dto.PasswordResetDTO;
import web.technologies.flixer.dto.RegistrationDTO;
import web.technologies.flixer.entity.Role;
import web.technologies.flixer.entity.User;
import web.technologies.flixer.entity.Validation;
import web.technologies.flixer.repository.AuthenticationRepository;
import web.technologies.flixer.repository.RoleRepository;
import web.technologies.flixer.service.exception.AlreadyProcessedException;
import web.technologies.flixer.service.exception.AlreadyUsedException;
import web.technologies.flixer.service.exception.NotYetEnabledException;
import web.technologies.flixer.service.exception.ValidationCodeException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Slf4j
@Transactional
@AllArgsConstructor
@Service
public class AuthenticationService implements UserDetailsService {
    private final AuthenticationRepository authenticationRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ValidationService validationService;

    public void signUp(RegistrationDTO userDTO) {
        Optional<User> dbUser;
        dbUser= this.authenticationRepository.findByEmail(userDTO.getEmail());

        User user;
        if(dbUser.isPresent()) {
            user = dbUser.get();
            if (user.getEnabled())
                throw new AlreadyUsedException("Email already used");

            dbUser = this.authenticationRepository.findByUsername(userDTO.getUsername());

            if(dbUser.isPresent())
                throw new AlreadyUsedException("Username already used");

            String encryptedPassword = this.passwordEncoder.encode(userDTO.getPassword());
            Instant now = Instant.now();
            Role role = this.roleRepository.getRoleById(userDTO.getRoleId());

            user.setUsername(userDTO.getUsername());
            user.setPassword(encryptedPassword);
            user.setBirthday(userDTO.getBirthday());
            user.setCreatedAt(now);
            user.setLastUpdate(now);
            user.setRole(role);
        } else {
            dbUser = this.authenticationRepository.findByUsername(userDTO.getUsername());

            if(dbUser.isPresent())
                throw new AlreadyUsedException("Username already used");

            String encryptedPassword = this.passwordEncoder.encode(userDTO.getPassword());
            Instant now = Instant.now();
            Role role = this.roleRepository.getRoleById(userDTO.getRoleId());

            user = User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(encryptedPassword)
                .birthday(userDTO.getBirthday())
                .amount(BigDecimal.ZERO)
                .createdAt(now)
                .lastUpdate(now)
                .enabled(false)
                .role(role)
                .build();
        }

        this.authenticationRepository.save(user);

        this.validationService.register(user);
    }

    public void activate(ActivationDTO activationDTO) {
        Validation validation = this.validationService.findUserActivationCode(
            activationDTO.getEmail(),
            activationDTO.getCode()
        );

        if(Instant.now().isAfter(validation.getExpiredAt()))
            throw new ValidationCodeException("Expired activation code");

        if(!validation.isEnabled())
            throw new ValidationCodeException("Disabled activation code");

        User user = validation.getUser();
        if(user.isEnabled())
            throw new AlreadyProcessedException("User already enabled");

        user.setEnabled(true);
        this.authenticationRepository.save(user);
    }

    public void newActivationCode(EmailDTO userDTO) {
        User user = this.authenticationRepository.findByEmail(userDTO.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(user.isEnabled())
            throw new AlreadyProcessedException("User already enabled");

        this.validationService.register(user);
    }

    public void resetPassword(EmailDTO userDTO) {
        User user = this.authenticationRepository.findByEmail(userDTO.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(!user.isEnabled())
            throw new NotYetEnabledException("User not yet enabled");

        this.validationService.resetPassword(user);
    }

    public void newPassword(PasswordResetDTO passwordResetDTO) {
        Validation validation = validationService.findUserPasswordResetCode(
            passwordResetDTO.getEmail(),
            passwordResetDTO.getCode()
        );

        User user = validation.getUser();
        if(!user.isEnabled())
            throw new NotYetEnabledException("User not yet enabled");

        if(Instant.now().isAfter(validation.getExpiredAt()))
            throw new ValidationCodeException("Expired password reset code");

        if(!validation.isEnabled())
            throw new ValidationCodeException("Disabled password reset code");

        String encryptedPassword = this.passwordEncoder.encode(passwordResetDTO.getPassword());
        user.setPassword(encryptedPassword);
        this.authenticationRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String username) {
        return this.authenticationRepository.findByEmailOrUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
