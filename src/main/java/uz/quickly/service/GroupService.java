package uz.quickly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.quickly.domain.Group;
import uz.quickly.domain.User;
import uz.quickly.repository.GroupRepo;
import uz.quickly.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class GroupService {
    final
    GroupRepo repoGroup;

    final
    UserRepository repoUser;

    @Autowired
    public GroupService(GroupRepo repoGroup,
                        UserRepository repoUser) {
        this.repoGroup = repoGroup;
        this.repoUser = repoUser;
    }


    public List<String> addToGroup(Group group, HttpServletRequest request) {
        boolean b = false;
        if (!group.getName().equals("")) {
            group.setName(group.getName().toLowerCase());

            Optional<Group> optionalGroup = repoGroup.findFirstByName(group.getName().trim());

            if (optionalGroup.isPresent()) {
                Group findByNameGroup = optionalGroup.get();
                if (findByNameGroup.getPassword().equals(group.getPassword())) {
                    User user = (User) request.getSession().getAttribute("user");
                    user.setGroups(findByNameGroup);
                    repoUser.save(user);
                    b = true;
                }
            }
            if (!repoGroup.existsByName(group.getName())) {
                Group savedGroup = repoGroup.save(group);
                User user = (User) request.getSession().getAttribute("user");
                user.setGroups(savedGroup);
                repoUser.save(user);
                b = true;
            }
        }
        if (b) {
            List<String> userNames = new ArrayList<>();
            List<User> allUserByGroupName = repoUser.findAllByGroups_Name(group.getName());
            for (User user : allUserByGroupName) {
                userNames.add(user.getUserName());
            }
            return userNames;
        } else {
            return null;
        }
    }

    public void deleteGroup(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        user.setGroups(null);
        Optional<User> byUserName = repoUser.findByUserName(user.getUserName());
        user.setRecordWpm(byUserName.orElse(user).getRecordWpm());
        repoUser.save(user);
    }
}
