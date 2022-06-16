package uz.quickly_write_html.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import uz.quickly_write_html.repository.GroupRepo;
import uz.quickly_write_html.repository.TextRepo;
import uz.quickly_write_html.repository.UserRepo;
import uz.quickly_write_html.service.GroupService;
import uz.quickly_write_html.service.UserService;


@Configuration
public class BeanConfig {

    @Bean
    @Scope("prototype")
    public UserService userService(UserRepo userRepo, GroupRepo groupRepo, TextRepo textRepo, GroupService groupService) {
        return new UserService(userRepo, groupRepo, textRepo, groupService);
    }
}
