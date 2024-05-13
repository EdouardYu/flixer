package web.technologies.flixer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.technologies.flixer.entity.User;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    @Query("SELECT U FROM User U WHERE U.email = :username OR U.username = :username")
    Optional<User> findByEmailOrUsername(String username);
}
