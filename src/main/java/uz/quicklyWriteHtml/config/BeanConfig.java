package uz.quicklyWriteHtml.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import uz.quicklyWriteHtml.repository.GroupRepo;
import uz.quicklyWriteHtml.repository.TextRepo;
import uz.quicklyWriteHtml.repository.UserRepo;
import uz.quicklyWriteHtml.service.GroupService;
import uz.quicklyWriteHtml.service.UserService;


@Configuration
public class BeanConfig {

    @Bean
    @Scope("prototype")
    public UserService userService(UserRepo userRepo, GroupRepo groupRepo, TextRepo textRepo, GroupService groupService) {
        return new UserService(userRepo, groupRepo, textRepo, groupService);
    }
}
