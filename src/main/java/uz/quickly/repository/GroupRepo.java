package uz.quickly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.quickly.entitiy.Group;

import java.util.Optional;

public interface GroupRepo extends JpaRepository<Group, Integer> {

    boolean existsByName(String name);

    Optional<Group> findFirstByName(String name);
}
