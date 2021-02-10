package com.topjava.CafeVote.web.restaurant;

import com.topjava.CafeVote.error.NotFoundException;
import com.topjava.CafeVote.model.Meal;
import com.topjava.CafeVote.service.MealService;
import com.topjava.CafeVote.util.JsonUtil;
import com.topjava.CafeVote.web.AbstractControllerTest;
import com.topjava.CafeVote.web.restaurant.admin.AdminRestaurantRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.topjava.CafeVote.RestaurantTestDataConstants.*;
import static com.topjava.CafeVote.TestUtil.*;
import static com.topjava.CafeVote.UserTestDataConstants.ADMIN;
import static com.topjava.CafeVote.error.ErrorType.DATA_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminMealControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantRestController.ADMIN_RESTAURANTS_REST_URL + '/';
    @Autowired
    private MealService mealService;

    /* --- meal --- */

    @Test
    void mealGetAllMeals() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/meals")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TEST_MATCHER.contentJson(MEAL_LIST));
    }

    @Test
    void mealGetAllMealsForRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_3ID + "/meals")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TEST_MATCHER.contentJson(RESTAURANT3_MEAL_LIST))
                .andDo(print());
    }

    @Test
    void mealGetMeal() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_3ID + "/meals/" + Meal7_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> MEAL_TEST_MATCHER.assertMatch(readFromJsonMvcResult(result, Meal.class), Meal7))
                .andDo(print());
    }

    @Test
    void mealGetNotRestaurantsMeal() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_3ID + "/meals/" + Meal15_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }


    @Test
    void mealDeleteMeal() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_3ID + "/meals/" + Meal7_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());

        //MEAL_TEST_MATCHER.assertMatch(mealService.getAll(RESTAURANT_3ID), Meal8, Meal9);
        assertThrows(NotFoundException.class, () -> mealService.get(Meal7_ID, RESTAURANT_3ID));
    }

    @Test
    void mealCreateMeal() throws Exception {
        Meal created = new Meal(null, "New meal");
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT_9ID + "/meals")
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isOk())
                .andDo(print());

        Meal returned = readFromJson(action, Meal.class);
        created.setId(returned.getId());
        MEAL_TEST_MATCHER.assertMatch(created, returned);

        Meal saved = mealService.get(returned.id(), RESTAURANT_9ID);
        MEAL_TEST_MATCHER.assertMatch(returned, saved);
    }


    @Test
    void mealCreateNotNewMeal() throws Exception {
        Meal notNewMeal = new Meal(1001, "Fries");
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT_1ID + "/meals")
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(notNewMeal)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }


    @Test
    void mealUpdateMeal() throws Exception {
        Meal updated = new Meal(Meal11_ID, "Updated Meal");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_4ID + "/meals")
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent())
                .andDo(print());

        MEAL_TEST_MATCHER.assertMatch(mealService.get(Meal11_ID, RESTAURANT_4ID), updated);
    }
}
