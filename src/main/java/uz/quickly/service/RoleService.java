package uz.quickly.service;

import org.springframework.stereotype.Service;
import uz.quickly.domain.Role;
import uz.quickly.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByRolName(String rolName) {
        return roleRepository.findByRolName(rolName);
    }
}
