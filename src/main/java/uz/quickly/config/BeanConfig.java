package uz.quickly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import uz.quickly.repository.GroupRepo;
import uz.quickly.repository.TextRepo;
import uz.quickly.repository.UserRepo;
import uz.quickly.service.GroupService;
import uz.quickly.service.UserService;


@Configuration
public class BeanConfig {

    @Bean
    @Scope("prototype")
    public UserService userService(UserRepo userRepo, GroupRepo groupRepo, TextRepo textRepo, GroupService groupService) {
        return new UserService(userRepo, groupRepo, textRepo, groupService);
    }
}
