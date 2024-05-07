package web.technologies.flixer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.technologies.flixer.entity.History;
import web.technologies.flixer.entity.HistoryId;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, HistoryId> {
    List<History> findByUserId(Long userId);
}
