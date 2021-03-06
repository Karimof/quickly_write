package uz.quicklyWriteHtml.service;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.quicklyWriteHtml.entitiy.User;
import uz.quicklyWriteHtml.model.UserDto;
import uz.quicklyWriteHtml.repository.GroupRepo;
import uz.quicklyWriteHtml.repository.TextRepo;
import uz.quicklyWriteHtml.repository.UserRepo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;


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

    public boolean addUser(UserDto newUser, Model model,
                           MultipartHttpServletRequest multipart,
                           HttpServletRequest request) throws IOException {
        String message = "";
        if (userRepo.existsUserByUserName(newUser.getUserName())) {
            message = "Bunday foydalanuvchi mavjud!";
            model.addAttribute("message", message);
            return false;
        }
        if (newUser.getPassword().equals(newUser.getCheckPassword())) {
            // here comes user photo
            Iterator<String> fileNames = multipart.getFileNames();
            MultipartFile file = multipart.getFile(fileNames.next());
            User user = new User();
            if (!file.isEmpty()) {
                String fullOrgName = file.getOriginalFilename();

                // Hashing the photo name
                String[] split = fullOrgName.split("\\.");
                String hashName = UUID.randomUUID().toString() + "." + split[split.length - 1];
                user.setPhotoName(hashName);

                // Saving photo to file system
                Path path = Paths.get("uploads/photos/" + hashName);
                InputStream inputStream = file.getInputStream();
                Files.copy(inputStream, path);
                user.setPhotoName(hashName);
            }
            user.setFullName(newUser.getFullName());
            user.setUserName(newUser.getUserName());
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());
            userRepo.save(user);
            message = "Muvaffaqqiyatli ro'yhatdan o'tdingiz va tizimga kirdingiz. ";
            request.getSession().setAttribute("user", user);
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

    public Model isLogged(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getUserName());
            model.addAttribute("userPhoto", "/uploads/photos/" + user.getPhotoName());
            model.addAttribute("display", "");
            model.addAttribute("displayButton", "none");
        } else {
            model.addAttribute("display", "none");
            model.addAttribute("displayButton", "");
            model.addAttribute("userName", "");
        }
        return model;
    }

    public boolean isLogged(HttpServletRequest request) {
        return request.getSession().getAttribute("user") != null;
    }
}
