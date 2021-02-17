package com.topjava.cafevote.web.restaurant;

import com.topjava.cafevote.error.NotFoundException;
import com.topjava.cafevote.model.Menu;
import com.topjava.cafevote.service.MealService;
import com.topjava.cafevote.service.MenuService;
import com.topjava.cafevote.to.MenuTo;
import com.topjava.cafevote.util.JsonUtil;
import com.topjava.cafevote.util.ToUtil;
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
import static com.topjava.cafevote.TestUtil.readFromJson;
import static com.topjava.cafevote.TestUtil.userHttpBasic;
import static com.topjava.cafevote.UserTestDataConstants.ADMIN;
import static com.topjava.cafevote.error.ErrorType.DATA_ERROR;
import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminMenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantRestController.ADMIN_RESTAURANTS_REST_URL + '/';
    @Autowired
    private MealService mealService;
    @Autowired
    private MenuService menuService;

    /* --- menu --- */

    @Test
    void menuGetAllMenus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/menus")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENUTO_TEST_MATCHER.contentJson(ToUtil.menusAsToList(DAY_MENUS)));
    }

    @Test
    void menuGetAllMenusForRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_8ID + "/menus")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TEST_MATCHER.contentJson(RESTAURANT_8ID_MENUS))
                .andDo(print());
    }

    @Test
    void menuGetAllMenusForDayByRestaurantId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_8ID + "/menus/for")
                .param("date", "2021-01-11")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TEST_MATCHER.contentJson(MENU24, MENU26))
                .andDo(print());
    }

    @Test
    void menuGetAllMenusForDay() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/menus/for")
                .param("date", "2021-01-11")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TEST_MATCHER.contentJson(MENUS_FOR_20210111))
                .andDo(print());
    }

    @Test
    void menuGetMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_2ID + "/menus/" + MENU4_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENUTO_TEST_MATCHER.contentJson(new MenuTo(MENU4)))
                .andDo(print());
    }

    @Test
    void menuDeleteMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_7ID + "/menus/" + MENU37_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());

        MENU_TEST_MATCHER.assertMatch(menuService.getAllForRestaurantId(RESTAURANT_7ID), MENU9, MENU10, MENU11, MENU23);
        assertThrows(NotFoundException.class, () -> mealService.get(MENU37_ID, RESTAURANT_7ID));
    }

    @Test
    void menuDeleteAllMenuForDay() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_1ID + "/menus/for")
                .param("date", "2021-01-10")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertTrue(menuService.getAllForByDateRestaurantId(RESTAURANT_1ID, of(2021, 1, 10)).isEmpty());
    }

    @Test
    void menuCreateMenu() throws Exception {
        Menu created = new Menu(null, null, 420);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT_4ID + "/menus/" + Meal11_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isOk())
                .andDo(print());

        Menu returned = readFromJson(action, Menu.class);
        created.setId(returned.getId());
        MENU_TEST_MATCHER.assertMatch(created, returned);

        Menu saved = menuService.get(returned.id(), RESTAURANT_4ID);
        MENU_TEST_MATCHER.assertMatch(returned, saved);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void menuCreateDuplicateMenu() throws Exception {
        Menu created = new Menu(MENU27);
        created.setId(null);
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT_1ID + "/menus/" + Meal1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isConflict())
                .andExpect(errorType(DATA_ERROR))
                .andDo(print());
    }

    @Test
    void menuUpdateMenu() throws Exception {
        Menu updated = new Menu(MENU39);
        updated.setMenuDate(null);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_9ID + "/menus/" + Meal26_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent())
                .andDo(print());

        MENU_TEST_MATCHER.assertMatch(menuService.get(MENU39_ID, RESTAURANT_9ID), updated);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void menuUpdateWithAlreadyExistingMenuInThisDay() throws Exception {
        Menu updated = new Menu(MENU1);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_1ID + "/menus/" + Meal2_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isConflict())
                .andExpect(errorType(DATA_ERROR))
                .andDo(print());
    }
}
