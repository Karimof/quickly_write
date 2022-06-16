package uz.quickly_write_html.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uz.quickly_write_html.model.UserDto;
import uz.quickly_write_html.service.GroupService;
import uz.quickly_write_html.service.UserService;

import javax.servlet.http.HttpServletRequest;

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
        return "index";
    }

    @GetMapping("/register_form")
    public String regForm(Model model) {
        model.addAttribute("message", "");
        return "register_form";
    }

    @PostMapping(value = "/register")
    public String add(UserDto newUser, Model model) {
        // TODO index ni o'rniga redirect ishlatish kerak.
        model.addAttribute("fullName", newUser.getFullName());
        return userService.addUser(newUser, model) ? "index" : "register_form";
    }

    @GetMapping(value = "/login_form")
    public String log_form(Model model) {
        model.addAttribute("message", "");
        return "login_form";
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute(name = "userName") String userName
            , @ModelAttribute(name = "password") String password,
                        Model model, HttpServletRequest request) {
        boolean passed = userService.loginService(userName, password, model, request);
        if (passed) {
            return "index";
        } else
            model.addAttribute("message", "Login yoki parol hato!");
        return "login_form";
    }
}
