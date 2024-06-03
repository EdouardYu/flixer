package web.technologies.flixer.repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.technologies.flixer.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional <User> findUserById(Long id);

    @Nonnull
    Page<User> findAll(@Nonnull Pageable pageable);

    boolean existsByUsername(String username);
}