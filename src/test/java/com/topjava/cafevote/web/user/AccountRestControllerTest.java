package com.topjava.cafevote.web.user;

import com.topjava.cafevote.model.User;
import com.topjava.cafevote.repository.UserRepository;
import com.topjava.cafevote.to.UserTo;
import com.topjava.cafevote.util.JsonUtil;
import com.topjava.cafevote.util.UserUtil;
import com.topjava.cafevote.web.AbstractControllerTest;
import com.topjava.cafevote.web.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.topjava.cafevote.TestUtil.readFromJson;
import static com.topjava.cafevote.TestUtil.userHttpBasic;
import static com.topjava.cafevote.UserTestDataConstants.*;
import static com.topjava.cafevote.web.user.AccountRestController.REST_ACCOUNT_URL;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ACCOUNT_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(USER1));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ACCOUNT_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_ACCOUNT_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(userRepository.findAll(), ADMIN, USER2, USER3, USER4, USER5, USER6, USER7, USER8);
    }

    @Test
    void register() throws Exception {
        UserTo newTo = new UserTo(null, "newFirstName", "newLastName", "newuser@yandex.ru", "password");
        User newUser = UserUtil.createNewFromTo(newTo);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_ACCOUNT_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        User created = readFromJson(action, User.class);
        int newId = created.id();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(userRepository.existsById(newId), newUser);
    }

    @Test
    void update() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", "newName", "user@yandex.ru", "newPassword");
        perform(MockMvcRequestBuilders.put(REST_ACCOUNT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_MATCHER.assertMatch(userRepository.existsById(USER_ID), UserUtil.updateFromTo(new User(USER1), updatedTo));
    }

    @Test
    void registerInvalid() throws Exception {
        UserTo newTo = new UserTo(null, null, null, "sauron@mail.ru", null);
        perform(MockMvcRequestBuilders.post(REST_ACCOUNT_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void registerWithInvalidId() throws Exception {
        UserTo newTo = new UserTo(1, "sauron", "sauron", "sauron@mail.ru", "password");
        perform(MockMvcRequestBuilders.post(REST_ACCOUNT_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateInvalid() throws Exception {
        UserTo updatedTo = new UserTo(null, null, "password", null, null);
        perform(MockMvcRequestBuilders.put(REST_ACCOUNT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateDuplicate() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", "newName", "admin@gmail.com", "newPassword");
        perform(MockMvcRequestBuilders.put(REST_ACCOUNT_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(GlobalExceptionHandler.EXCEPTION_DUPLICATE_EMAIL)));
    }
}