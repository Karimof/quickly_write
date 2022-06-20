package uz.quickly_write_html.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.quickly_write_html.model.UserDto;
import uz.quickly_write_html.service.GroupService;
import uz.quickly_write_html.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class UserController {
    final
    UserService userService;
    final
    GroupService groupService;

    public UserController(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }


    @GetMapping({"", "/", "/index"})
    public String index(HttpServletRequest request, Model model) {
        userService.isLoginned(request, model);
        model.addAttribute("content", "index");
        return "fragments/layout";
    }

    @GetMapping("/register_form")
    public String regForm(HttpServletRequest request, Model model) {
        userService.isLoginned(request, model);
        model.addAttribute("message", "");
        model.addAttribute("content", "register_form");
        return "fragments/layout";
    }

    @PostMapping(value = "/register")
    public String add(UserDto newUser, Model model,
                      MultipartHttpServletRequest multipart,
                      HttpServletRequest request) throws IOException {
        // TODO index ni o'rniga redirect ishlatish kerak.
        model.addAttribute("display", "none");
        model.addAttribute("content", userService.addUser(newUser, model, multipart, request) ? "index" : "register_form");
        return "fragments/layout";
    }

    @GetMapping(value = "/login")
    public String log_form(HttpServletRequest request, Model model) {
        userService.isLoginned(request, model);
        model.addAttribute("message", "");
        model.addAttribute("content", "login_form");
        return "fragments/layout";
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute(name = "userName") String userName
            , @ModelAttribute(name = "password") String password,
                        Model model,
                        HttpServletRequest request
    ) {

        boolean passed = userService.loginService(userName, password, model, request);
        userService.isLoginned(request, model);
        if (passed) {
            model.addAttribute("content", "index");
            return "fragments/layout";
        } else
            model.addAttribute("message", "Login yoki parol hato!");
        model.addAttribute("content", "login_form");
        return "fragments/layout";
    }
}
