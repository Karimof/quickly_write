package uz.quickly_write_html.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.quickly_write_html.entitiy.Group;

import java.util.Optional;

public interface GroupRepo extends JpaRepository<Group, Integer> {
    boolean existsByName(String name);

    Optional<Group> findByName(String name);
}
