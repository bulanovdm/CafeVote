package com.topjava.CafeVote.web.restaurant;

import com.topjava.CafeVote.error.ExceptionInfoHandler;
import com.topjava.CafeVote.error.NotFoundException;
import com.topjava.CafeVote.model.Restaurant;
import com.topjava.CafeVote.service.MealService;
import com.topjava.CafeVote.service.MenuService;
import com.topjava.CafeVote.service.RestaurantService;
import com.topjava.CafeVote.util.JsonUtil;
import com.topjava.CafeVote.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.topjava.CafeVote.RestaurantTestData.*;
import static com.topjava.CafeVote.TestUtil.*;
import static com.topjava.CafeVote.UserTestData.ADMIN;
import static com.topjava.CafeVote.UserTestData.USER2;
import static com.topjava.CafeVote.error.ErrorType.DATA_NOT_FOUND;
import static com.topjava.CafeVote.error.ErrorType.VALIDATION_ERROR;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MealService mealService;

    @Autowired
    private MenuService menuService;

    private static final String REST_URL = AdminRestaurantRestController.ADMIN_RESTAURANTS_REST_URL + '/';

    //restaurants
    @Test
    void testCreate() throws Exception {
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
        Restaurant saved = restaurantService.get(returned.getId());
        RESTAURANT_TEST_MATCHER.assertMatch(returned, saved);
    }

    @Test
    void testUpdate() throws Exception {
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
    void testGetAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TEST_MATCHER.contentJson(RESTAURANT_LIST));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_1ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> RESTAURANT_TEST_MATCHER.assertMatch(readFromJsonMvcResult(result, Restaurant.class), REST_1));
    }

    @Test
    void testGetUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_1ID))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void testGetForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_1ID)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID_NOT_FOUND)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_1ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());

        //RESTAURANT_TEST_MATCHER.assertMatch(restaurantService.getAllForDay(LocalDate.of(2021, 1, 10)), RESTAURANTS_FOR_DELETE_TEST);
        assertThrows(NotFoundException.class, () -> restaurantService.get(RESTAURANT_1ID));


    }

    @Test
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_ID_NOT_FOUND)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                //.andExpect(errorType(DATA_NOT_FOUND))
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void testGetForDay() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_2ID + "/for")
                .param("day", "2021-01-10")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TEST_MATCHER.contentJson(REST_2));
    }

    @Test
    void testGetForDayNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_6ID + "/for")
                .param("day", "2021-01-10")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                //.andExpect(errorType(DATA_NOT_FOUND))
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void testGetAllForDay() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/for")
                .param("day", "2021-01-10")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TEST_MATCHER.contentJson(REST_2, REST_3, REST_1, REST_5, REST_7, REST_4, REST_9));
    }

    @Test
    void testCreateInvalid() throws Exception {
        Restaurant invalid = new Restaurant(123, "not null id");
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void testUpdateInvalidName() throws Exception {
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
    void testCreateDuplicateName() throws Exception {
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
    void testUpdateDuplicateName() throws Exception {
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




