package web.technologies.flixer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.technologies.flixer.entity.Subscription;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Stream;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("SELECT S FROM Subscription S " +
        "WHERE S.user.id = :userId " +
        "AND S.status.label IN ('STARTED', 'RENEWED') " +
        "AND S.startedAt <= :now AND S.endedAt >= :now")
    Optional<Subscription> findActiveSubscriptionByUserId(Long userId, Instant now);

    @Query("SELECT S FROM Subscription S WHERE S.user.id = :id")
    Stream<Subscription> findUserSubscriptions(Long id);
}

