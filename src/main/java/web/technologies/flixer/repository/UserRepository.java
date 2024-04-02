package web.technologies.flixer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.technologies.flixer.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
