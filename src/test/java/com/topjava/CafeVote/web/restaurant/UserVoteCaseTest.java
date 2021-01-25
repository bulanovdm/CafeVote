package com.topjava.CafeVote.web.restaurant;

import com.topjava.CafeVote.model.Meal;
import com.topjava.CafeVote.model.Menu;
import com.topjava.CafeVote.model.Restaurant;
import com.topjava.CafeVote.model.Vote;
import com.topjava.CafeVote.util.JsonUtil;
import com.topjava.CafeVote.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static com.topjava.CafeVote.RestaurantTestDataConstants.*;
import static com.topjava.CafeVote.TestUtil.*;
import static com.topjava.CafeVote.UserTestDataConstants.ADMIN;
import static com.topjava.CafeVote.UserTestDataConstants.USER1;
import static com.topjava.CafeVote.web.restaurant.AdminRestaurantRestController.ADMIN_RESTAURANTS_REST_URL;
import static com.topjava.CafeVote.web.restaurant.UserRestaurantRestController.USER_RESTAURANTS_REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserVoteCaseTest extends AbstractControllerTest {

    private static final String REST_URL = ADMIN_RESTAURANTS_REST_URL + '/';

    @Test
    void testFullCase() throws Exception {
        //create new restaurant, check
        ResultActions restaurantAction = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new Restaurant(null, "New Restaurant"))))
                .andExpect(status().isCreated())
                .andDo(print());
        Restaurant newRestaurant = readFromJson(restaurantAction, Restaurant.class);

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + newRestaurant.getId())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> RESTAURANT_TEST_MATCHER.assertMatch(readFromJsonMvcResult(result, Restaurant.class), newRestaurant));

        //create new meal, check
        ResultActions meal = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + newRestaurant.getId() + "/meals")
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new Meal(null, "New Meal"))))
                .andExpect(status().isOk())
                .andDo(print());
        Meal newMeal = readFromJson(meal, Meal.class);

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + newRestaurant.getId() + "/meals")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TEST_MATCHER.contentJson(Collections.singleton(newMeal)))
                .andDo(print());

        //create new Menu, check
        ResultActions dayMenuAction = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + newRestaurant.getId() + "/menus/" + newMeal.getId())
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new Menu(null, null, 420))))
                .andExpect(status().isOk())
                .andDo(print());
        Menu menu = readFromJson(dayMenuAction, Menu.class);

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + newRestaurant.getId())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> RESTAURANT_TEST_MATCHER.assertMatch(readFromJsonMvcResult(result, Restaurant.class), newRestaurant));

        //voting for new Restaurant with menu, check
        ResultActions voteActions = mockMvc.perform(MockMvcRequestBuilders.post(USER_RESTAURANTS_REST_URL + "/" + newRestaurant.getId() + "/vote")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isCreated())
                .andDo(print());
        Vote newVote = readFromJson(voteActions, Vote.class);

        mockMvc.perform(MockMvcRequestBuilders.get(USER_RESTAURANTS_REST_URL + "/" + newRestaurant.getId() + "/vote/" + newVote.getId())
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> VOTE_TEST_MATCHER.assertMatch(readFromJsonMvcResult(result, Vote.class), newVote));
    }
}
