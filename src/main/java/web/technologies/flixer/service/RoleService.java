package web.technologies.flixer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import web.technologies.flixer.entity.Role;
import web.technologies.flixer.repository.RoleRepository;

@AllArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRoleById(Long id){
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("No Role exist for the id " + id.toString()));
    }
}
