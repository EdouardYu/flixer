package web.technologies.flixer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.technologies.flixer.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Role getRoleById(Long id);
}
