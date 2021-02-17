package com.topjava.cafevote.web.restaurant;

import com.topjava.cafevote.error.ExceptionInfoHandler;
import com.topjava.cafevote.error.NotFoundException;
import com.topjava.cafevote.model.Restaurant;
import com.topjava.cafevote.service.RestaurantService;
import com.topjava.cafevote.util.JsonUtil;
import com.topjava.cafevote.web.AbstractControllerTest;
import com.topjava.cafevote.web.restaurant.admin.AdminRestaurantRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.topjava.cafevote.RestaurantTestDataConstants.*;
import static com.topjava.cafevote.TestUtil.*;
import static com.topjava.cafevote.UserTestDataConstants.ADMIN;
import static com.topjava.cafevote.UserTestDataConstants.USER2;
import static com.topjava.cafevote.error.ErrorType.DATA_NOT_FOUND;
import static com.topjava.cafevote.error.ErrorType.VALIDATION_ERROR;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantRestController.ADMIN_RESTAURANTS_REST_URL + '/';
    @Autowired
    private RestaurantService restaurantService;

    /* --- restaurants --- */

    @Test
    void restaurantCreate() throws Exception {
        Restaurant created = new Restaurant(null, "TestingRestaurant");
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated())
                .andDo(print());

        Restaurant returned = readFromJson(action, Restaurant.class);
        created.setId(returned.getId());
        RESTAURANT_TEST_MATCHER.assertMatch(created, returned);
        Restaurant saved = restaurantService.get(returned.id());
        RESTAURANT_TEST_MATCHER.assertMatch(returned, saved);
    }

    @Test
    void restaurantUpdate() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT_1ID, "New restaurant");

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        RESTAURANT_TEST_MATCHER.assertMatch(restaurantService.get(RESTAURANT_1ID), updated);
    }


    @Test
    void restaurantGetAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TEST_MATCHER.contentJson(RESTAURANT_LIST));
    }

    @Test
    void restaurantGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_1ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> RESTAURANT_TEST_MATCHER.assertMatch(readFromJsonMvcResult(result, Restaurant.class), REST_1));
    }

    @Test
    void restaurantGetUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_1ID))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void restaurantGetForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_1ID)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void restaurantGetNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID_NOT_FOUND)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }

    @Test
    void restaurantDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_1ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());

        //RESTAURANT_TEST_MATCHER.assertMatch(restaurantService.getAllForDay(LocalDate.of(2021, 1, 10)), RESTAURANTS_FOR_DELETE_TEST);
        assertThrows(NotFoundException.class, () -> restaurantService.get(RESTAURANT_1ID));


    }

    @Test
    void restaurantDeleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_ID_NOT_FOUND)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                //.andExpect(errorType(DATA_NOT_FOUND))
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }

    @Test
    void restaurantGetForDay() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_2ID + "/for")
                .param("date", "2021-01-10")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TEST_MATCHER.contentJson(REST_2));
    }

    @Test
    void restaurantGetForDayNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_6ID + "/for")
                .param("date", "2021-01-10")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                //.andExpect(errorType(DATA_NOT_FOUND))
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }

    @Test
    void restaurantGetAllForDay() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/for")
                .param("date", "2021-01-10")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TEST_MATCHER.contentJson(REST_2, REST_3, REST_1, REST_5, REST_7, REST_4, REST_9));
    }

    @Test
    void restaurantCreateInvalid() throws Exception {
        Restaurant invalid = new Restaurant(123, "not null id");
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }

    @Test
    void restaurantUpdateInvalidName() throws Exception {
        Restaurant invalid = new Restaurant(REST_3);
        invalid.setName(null);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print())
                .andExpect(detailMessage("name must not be blank"));
    }


    @Test
    @Transactional(propagation = Propagation.NEVER)
    void restaurantCreateDuplicateName() throws Exception {
        Restaurant duplicate = new Restaurant(null, REST_5.getName());
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(duplicate)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print())
                .andExpect(detailMessage(ExceptionInfoHandler.EXCEPTION_DUPLICATE_RESTAURANT_NAME));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void restaurantUpdateDuplicateName() throws Exception {
        Restaurant duplicate = new Restaurant(RESTAURANT_5ID, REST_6.getName());
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(duplicate)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print())
                .andExpect(detailMessage(ExceptionInfoHandler.EXCEPTION_DUPLICATE_RESTAURANT_NAME));
    }
}




