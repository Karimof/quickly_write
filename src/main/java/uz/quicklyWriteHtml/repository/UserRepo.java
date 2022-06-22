package uz.quicklyWriteHtml.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.quicklyWriteHtml.entitiy.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    boolean existsUserByUserName(String userName);

    Optional<User> findByUserName(String userName);


    List<User> findAllByGroup_Name(String groupName);

}
