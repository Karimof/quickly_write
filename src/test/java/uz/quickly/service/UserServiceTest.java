package uz.quickly.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import uz.quickly.controller.UserController;
import uz.quickly.domain.User;
import uz.quickly.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    User user = getUser();

    public User getUser() {
        User user2 = new User("aaa", "aaa", "islamkarimov8580@gmail.com", "aaa");
        user2.setId(1L);
        user2.setActive(true);
        user2.setRecordWpm(6L);
        return user2;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
//        this.mockMvc = MockMvcBuilder
    }
}