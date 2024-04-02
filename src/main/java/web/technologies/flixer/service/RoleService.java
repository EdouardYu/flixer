package web.technologies.flixer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.technologies.flixer.entity.Role;
import web.technologies.flixer.repository.RoleRepository;

import java.util.Optional;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> getRoleById(Long id){
        return roleRepository.findById(id);
    }
}
