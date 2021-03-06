package com.topjava.cafevote.web.restaurant;

import com.topjava.cafevote.web.AbstractControllerTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static com.topjava.cafevote.RestaurantTestDataConstants.*;
import static com.topjava.cafevote.TestUtil.userHttpBasic;
import static com.topjava.cafevote.UserTestDataConstants.ADMIN;
import static com.topjava.cafevote.web.restaurant.admin.AdminRestaurantRestController.ADMIN_RESTAURANTS_REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminVoteControllerTest extends AbstractControllerTest {


    private static final String REST_URL = ADMIN_RESTAURANTS_REST_URL + '/';

    @Test
    void testGetAllVotesForTodayForRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_9ID + "/votes")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTETO_TEST_MATCHER.contentJson(new ArrayList<>()))
                .andDo(print());
    }

    @Test
    void testGetAllVotesForDayForRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_8ID + "/votes/for")
                .param("date", "2021-01-12")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTETO_TEST_MATCHER.contentJson(VOTE_TOS_FOR_20210111_FOR_RES8))
                .andDo(print());
    }
}
