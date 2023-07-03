package uz.quickly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.quickly.domain.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRolName(String rolName);
}
