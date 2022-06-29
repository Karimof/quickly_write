package uz.quicklyWriteHtml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uz.quicklyWriteHtml.entitiy.Group;
import uz.quicklyWriteHtml.entitiy.User;
import uz.quicklyWriteHtml.repository.UserRepo;
import uz.quicklyWriteHtml.service.GroupService;
import uz.quicklyWriteHtml.service.SinovService;
import uz.quicklyWriteHtml.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SinovController {

    final
    SinovService sinovService;
    final
    GroupService groupService;

    final
    UserService userService;
    @Autowired //TODO Remove here the userRepo
    UserRepo repoUser;

    public SinovController(SinovService sinovService, GroupService groupService, UserService userService) {
        this.sinovService = sinovService;
        this.groupService = groupService;
        this.userService = userService;
    }

    @GetMapping("/sinov")
    public String sinov(Model model, HttpServletRequest request) {
        userService.isLogged(request, model);
        forTests("sinov", request, model);
        return "fragments/layout";
    }

    @GetMapping("/guruh")
    public String guruhUrl(HttpServletRequest request, Model model) {
        userService.isLogged(request, model);
        forTests("guruh", request, model);
        return "fragments/layout";
    }

    @GetMapping("/guruhWindow")
    public String guruh(Group group, HttpServletRequest request, Model model) {
        List<String> userNames = groupService.addToGroup(group, request);
        userService.isLogged(request, model);
        if (userNames != null) {
            User currentUser = (User) request.getSession().getAttribute("user");
            model.addAttribute("gName", group.getName());
            model.addAttribute("gPass", group.getPassword());
            model.addAttribute("userNames", userNames);
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("text", sinovService.findByRandomId().getText());
            model.addAttribute("content", "guruhmusoboqasi");
        } else {
            model.addAttribute("message", "Bunday nomli guruh mavjud. Yoki parol hato!");
            model.addAttribute("content", "guruh");
        }
        return "fragments/layout";
    }

    private Model forTests(String pageName, HttpServletRequest request, Model model) {
        boolean logged = userService.isLogged(request);
        if (logged) {
            groupService.deleteGroup(request);
            if (pageName.equals("sinov"))
                model.addAttribute("text", sinovService.findByRandomId().getText());
            model.addAttribute("userName", ((User) request.getSession().getAttribute("user")).getUserName());
            model.addAttribute("content", pageName);
        } else {
            model.addAttribute("content", "login_form");
            model.addAttribute("plcSingIng", "Iltimos avval tizimga kiring");
        }
        return model;
    }
}
