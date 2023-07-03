package uz.quickly.controller;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import uz.quickly.domain.User;
import uz.quickly.model.EditUserDTO;
import uz.quickly.model.UserDto;
import uz.quickly.service.GroupService;
import uz.quickly.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    final
    UserService userService;
    final
    GroupService groupService;

    public UserController(UserService userService,
                          GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @GetMapping({"", "/", "/index"})
    public String index(HttpServletRequest request, Model model) {
        userService.isLogged(request, model);
        model.addAttribute("content", "index");
        return "fragments/layout";
    }

    @GetMapping("/register_form")
    public String regForm(HttpServletRequest request, Model model) {
//        String s = SecurityUtils.findCurrentCustomer();
        userService.isLogged(request, model);
        model.addAttribute("message", "");
        model.addAttribute("content", "register_form");
        return "fragments/layout";
    }

    @PostMapping(value = "/register")
    public RedirectView add(UserDto newUser, Model model,
                            MultipartHttpServletRequest multipart,
                            HttpServletRequest request, RedirectAttributes redirectAttrs) throws IOException {
        model.addAttribute("display", "none");
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);

        if (userService.addUser(newUser, model, multipart, request)) {
            redirectView.setUrl("index");
        } else {
            redirectView.setUrl("register_form");
        }
        return redirectView;
    }

    @GetMapping(value = "/login")
    public String log_form(HttpServletRequest request, Model model) {
        model = userService.isLogged(request, model);
        model.addAttribute("message", "");
        model.addAttribute("content", "login_form");
        return "fragments/layout";
    }

    @PostMapping(value = "/login")
    public RedirectView login(
            @ModelAttribute(name = "userName") String userName,
            @ModelAttribute(name = "password") String password,
            Model model,
            HttpServletRequest request
    ) {

        boolean passed = userService.loginService(userName, password, model, request);
        userService.isLogged(request, model);
        RedirectView redirectView = new RedirectView();
        if (passed) {
            redirectView.setUrl("index");
            return redirectView;
        } else {
            redirectView.setContextRelative(true);
            redirectView.setUrl("/login2");
        }
        return redirectView;
    }

    @RequestMapping(value = "login2")
    public String bir(Model model) {
        model.addAttribute("message", "Login yoki parol hato!");
        model.addAttribute("content", "login_form");
        return "fragments/layout";
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpServletRequest request, Model model) {
        request.getSession().setAttribute("user", null);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("index");
        return redirectView;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        return modelAndView;
    }

    // BEGINNING FROM HERE ADMIN METHODS, SURE TEMPORARY ;-)

    @GetMapping("admin/index")
    public String index(Model model) {
        List<User> userList = userService.getUserList();
        model.addAttribute("userList", userList);
        return "adminIndex";
    }

    @GetMapping("admin/get/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<User> optionalUser = userService.getUser(id);
        if (optionalUser.isEmpty()) {
            return "adminIndex";
        }
        User user = optionalUser.get();
        model.addAttribute("user", user);
        return "adminEditUser";
    }

    @PostMapping("admin/update/{id}")
    public String update(EditUserDTO editUserDto, @PathVariable Integer id, Model model, MultipartHttpServletRequest multipart) throws IOException {
        userService.updateUser(editUserDto, id, model, multipart);
        return "adminEditUser";
    }
}
