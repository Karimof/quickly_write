package uz.quickly_write_html.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uz.quickly_write_html.entitiy.Group;
import uz.quickly_write_html.entitiy.User;
import uz.quickly_write_html.repository.UserRepo;
import uz.quickly_write_html.service.GroupService;
import uz.quickly_write_html.service.SinovService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SinovController {

    final
    SinovService sinovService;
    final
    GroupService groupService;
    @Autowired //TODO Remove here the userRepo
    UserRepo repoUser;

    public SinovController(SinovService sinovService, GroupService groupService) {
        this.sinovService = sinovService;
        this.groupService = groupService;
    }

    @GetMapping("/sinov")
    public String sinov(Model model, HttpServletRequest request) {
        groupService.deleteGroup(request);
        model.addAttribute("text", sinovService.findByRandomId().getText());
        return "sinov";
    }

    @GetMapping("/guruh")
    public String guruhUrl(HttpServletRequest request, Model model) {
        groupService.deleteGroup(request);
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("userName", user.getUserName());
        return "guruh";
    }

    @GetMapping("/guruhWindow")
    public String guruh(Group group, HttpServletRequest request, Model model) {
        List<String> userNames = groupService.addToGroup(group, request);
        if (userNames != null) {
            User currentUser = (User) request.getSession().getAttribute("user");
            model.addAttribute("gName", group.getName());
            model.addAttribute("gPass", group.getPassword());
            model.addAttribute("userNames", userNames);
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("text", sinovService.findByRandomId().getText());
            return "guruhmusoboqasi";
        } else {
            model.addAttribute("message", "Bunday nomli guruh mavjud. Yoki parol hato!");
            return "guruh";
        }
    }
}
