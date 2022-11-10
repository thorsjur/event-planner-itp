package eventplanner.rest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import eventplanner.core.User;
import eventplanner.json.CustomObjectMapper;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { UserController.class, RestServiceApplication.class })
@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String SERVICE_PATH = "http://localhost:8080/";
    private static final CustomObjectMapper MAPPER = new CustomObjectMapper();

    private List<String> tempUserEmails = new ArrayList<>();

    @BeforeEach
    void beforeEach() throws Exception {
        User user = getTestUser("TEST@EXAMPLE.TEST", "TEST_PASSWORD");
        addTestUser(user);
        tempUserEmails.add(user.email());
    }

    @AfterEach
    void afterEach() throws Exception {
        for (String email : tempUserEmails) {
            removeTestUser(email);
        }
        tempUserEmails.clear();
    }

    @Test
    void testUser_retrievesUserAsExpected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(SERVICE_PATH + "user/get?email=" + tempUserEmails.get(0))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void testCreate() throws Exception {
        User user = getTestUser("TEST_NAME@EXAMPLE.test", "PASSWORD");
        tempUserEmails.add(user.email());

        mockMvc.perform(MockMvcRequestBuilders.post(SERVICE_PATH + "user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertDoesNotThrow(() -> loadUser(user.email()));
    }

    @Test
    void testDelete() throws Exception {
        User user = getTestUser("TEST_NAME@EXAMPLE.test", "PASSWORD");
        addTestUser(user);

        mockMvc.perform(MockMvcRequestBuilders.delete(SERVICE_PATH + "user/" + user.email())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThrows(MismatchedInputException.class, () -> loadUser(user.email()));
    }

    

    private User getTestUser(String email, String password) {
        return new User(email, password, true);
    }

    private void addTestUser(User user) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(SERVICE_PATH + "user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(user))
                .accept(MediaType.APPLICATION_JSON));
    }

    private void removeTestUser(String email) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(SERVICE_PATH + "user/" + email)
                .accept(MediaType.APPLICATION_JSON));
    }

    private User loadUser(String email) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(SERVICE_PATH + "user/get?email=" + email)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = result.getResponse().getContentAsString();
        return getUser(response);
    }

    private User getUser(String content) throws Exception {
        return MAPPER.readValue(content, User.class);
    }
}
