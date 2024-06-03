package web.technologies.flixer.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import web.technologies.flixer.dto.*;
import web.technologies.flixer.entity.*;
import web.technologies.flixer.repository.*;
import web.technologies.flixer.service.exception.AlreadyUsedException;
import web.technologies.flixer.service.exception.BadPasswordException;
import web.technologies.flixer.service.exception.InsufficientAmountException;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final HistoryRepository historyRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final SubscriptionStatusRepository subscriptionStatusRepository;

    private final BigDecimal SUBSCRIPTION_PRICE = BigDecimal.valueOf(9.99);

    public Page<User> getUsers(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    public UserDTO getUserById(Long id) {
        User user = hasPermission(id);

        Subscription activeSubscription = this.subscriptionRepository
            .findActiveSubscriptionByUserId(id, Instant.now()).orElse(null);

        return UserDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .birthday(user.getBirthday())
            .amount(user.getAmount())
            .role(user.getRole())
            .activeSubscription(activeSubscription)
            .build();
    }

    public List<HistoryUserDTO> getHistoryByUserId(Long id) {
         List<History> histories = historyRepository.findByUserId(id);
         List<HistoryUserDTO> historiesUser = new ArrayList<>();
         for (History history: histories) {
             historiesUser.add(new HistoryUserDTO(history.getMovie(), history.getWatched_at()));
         }
         return historiesUser;
     }

    public UserDTO modifyUser(Long id, UserModificationDTO userDTO) {
        User user = hasPermission(id);

        if(this.userRepository.existsByUsername(userDTO.getUsername())
            && !user.getUsername().equals(userDTO.getUsername()))
            throw new AlreadyUsedException("Username already exists");

        Instant now = Instant.now();

        user.setUsername(userDTO.getUsername());
        user.setBirthday(userDTO.getBirthday());
        user.setLastUpdate(now);

        user = this.userRepository.save(user);

        Subscription activeSubscription = this.subscriptionRepository
            .findActiveSubscriptionByUserId(id, now).orElse(null);

        return UserDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .birthday(user.getBirthday())
            .amount(user.getAmount())
            .role(user.getRole())
            .activeSubscription(activeSubscription)
            .build();
    }


    public UserDTO creditAccount(Long id, CreditAccountDTO userDTO) {
        User user = hasPermission(id);

        Instant now = Instant.now();

        user.setAmount(user.getAmount().add(userDTO.getAmount()));
        user.setLastUpdate(now);
        user = this.userRepository.save(user);

        Subscription activeSubscription = this.subscriptionRepository
            .findActiveSubscriptionByUserId(id, now).orElse(null);

        return UserDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .birthday(user.getBirthday())
            .amount(user.getAmount())
            .role(user.getRole())
            .activeSubscription(activeSubscription)
            .build();
    }


    public void modifyPassword(Long id, PasswordModificationDTO userDTO) {
        User user = hasPermission(id);

        if (!this.passwordEncoder.matches(userDTO.getOldPassword(), user.getPassword())) {
            throw new BadPasswordException("Incorrect password");
        }

        String newEncryptedPassword = this.passwordEncoder.encode(userDTO.getNewPassword());

        user.setPassword(newEncryptedPassword);
        user.setLastUpdate(Instant.now());
        this.userRepository.save(user);
    }

    private User hasPermission(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User dbUser = this.userRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("No user exist with the id " + id));

        if(!user.getId().equals(dbUser.getId()) && !"ADMINISTRATOR".equals(user.getRole().getLabel()))
            throw new AccessDeniedException("Access denied");

        return dbUser;
    }

    public UserDTO subscribe(Long userId) {
        User user = hasPermission(userId);

        if(user.getAmount().subtract(SUBSCRIPTION_PRICE).compareTo(BigDecimal.ZERO) < 0)
            throw new InsufficientAmountException("Transaction failed, insufficient amount in your account");

        Instant now = Instant.now();
        Optional<Subscription> activeSubscription = this.subscriptionRepository
            .findActiveSubscriptionByUserId(userId, now);


        Subscription subscription;
        if(activeSubscription.isPresent()) {
            subscription = activeSubscription.get();
            // RENEWED
            SubscriptionStatus status = this.subscriptionStatusRepository.getReferenceById(2L);

            subscription.setStatus(status);
            subscription.setRenewedAt(now);
            subscription.setEndedAt(subscription.getEndedAt().plus(31, ChronoUnit.DAYS));
        } else {
            //delete expired subscriptions:
            disableSubscriptions(userId);

            SubscriptionPlan plan = this.subscriptionPlanRepository.getReferenceById(1L);
            // STARTED
            SubscriptionStatus status = this.subscriptionStatusRepository.getReferenceById(1L);

            subscription = Subscription.builder()
                .user(user)
                .plan(plan)
                .status(status)
                .subscribedAt(now)
                .startedAt(now)
                .endedAt(now.plus(31, ChronoUnit.DAYS))
                .build();

        }
        subscription = this.subscriptionRepository.save(subscription);

        user.setAmount(user.getAmount().subtract(SUBSCRIPTION_PRICE));
        user.setLastUpdate(now);
        user = this.userRepository.save(user);

        return UserDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .birthday(user.getBirthday())
            .amount(user.getAmount())
            .role(user.getRole())
            .activeSubscription(subscription)
            .build();
    }

    private void disableSubscriptions(Long userId) {
        //EXPIRED
        SubscriptionStatus status = this.subscriptionStatusRepository.getReferenceById(4L);

        List<Subscription> subscriptions = this.subscriptionRepository.findUserSubscriptions(userId)
            .peek(sub -> sub.setStatus(status))
            .toList();

        this.subscriptionRepository.saveAll(subscriptions);
    }
}