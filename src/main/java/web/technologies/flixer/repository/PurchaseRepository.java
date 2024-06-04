package web.technologies.flixer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.technologies.flixer.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    boolean existsByUserIdAndMovieId(Long user_id, Long movie_id);
}

