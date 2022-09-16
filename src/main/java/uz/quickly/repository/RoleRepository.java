package uz.quickly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.quickly.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRolName(String rolName);
}
