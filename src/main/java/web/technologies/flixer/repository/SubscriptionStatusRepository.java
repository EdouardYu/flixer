package web.technologies.flixer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.technologies.flixer.entity.SubscriptionStatus;

public interface SubscriptionStatusRepository extends JpaRepository<SubscriptionStatus, Long> {
}

