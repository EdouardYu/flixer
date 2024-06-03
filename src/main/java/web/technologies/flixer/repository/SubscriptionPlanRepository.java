package web.technologies.flixer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.technologies.flixer.entity.SubscriptionPlan;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {
}

