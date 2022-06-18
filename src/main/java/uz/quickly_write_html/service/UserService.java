package uz.quickly_write_html.service;

import org.springframework.ui.Model;
import uz.quickly_write_html.entitiy.User;
import uz.quickly_write_html.model.UserDto;
import uz.quickly_write_html.repository.GroupRepo;
import uz.quickly_write_html.repository.TextRepo;
import uz.quickly_write_html.repository.UserRepo;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


public class UserService {
    final
    UserRepo userRepo;
    final
    GroupRepo groupRepo;
    final
    TextRepo textRepo;
    final
    GroupService groupService;

    public UserService(UserRepo userRepo, GroupRepo groupRepo, TextRepo textRepo, GroupService groupService) {
        this.userRepo = userRepo;
        this.groupRepo = groupRepo;
        this.textRepo = textRepo;
        this.groupService = groupService;
    }

    public boolean addUser(UserDto newUser, Model model) {
        String message = "";
        if (userRepo.existsUserByUserName(newUser.getUserName())) {
            message = "Bunday foydalanuvchi mavjud!";
            model.addAttribute("message", message);
            return false;
        }
        if (newUser.getPassword().equals(newUser.getCheckPassword())) {
            User user = new User(newUser.getFullName(), newUser.getUserName(), newUser.getEmail(), newUser.getPassword());
            userRepo.save(user);
            message = "Muvaffaqqiyatli ro'yhatdan o'tdingiz.";
            model.addAttribute("message", message);
            return true;
        }
        message = "Parolni to'g'ri takrorlang";
        model.addAttribute("message", message);
        return false;
    }

    public boolean loginService(String userName, String password, Model model, HttpServletRequest request) {
        Optional<User> optionalUser = userRepo.findByUserName(userName);
        if (optionalUser.isEmpty()) {
            return false;
        }
        User user = optionalUser.get();
        if (!user.getPassword().equals(password)) {
            return false;
        }
        user.setActive(true);
        User savedUser = userRepo.save(user);

        request.getSession().setAttribute("user", savedUser);
        model.addAttribute("message", "Tizimga kirdingiz");
        return true;
    }

    public Model isLoginned(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            groupService.deleteGroup(request);
            model.addAttribute("userName", user.getUserName());
            model.addAttribute("visibility", "visible");
            model.addAttribute("forHiddenButton", "hidden");

        } else {
            model.addAttribute("visibility", "hidden");
            model.addAttribute("forHiddenButton", "visible");
            model.addAttribute("userName", "");
        }
        return model;
    }
}
