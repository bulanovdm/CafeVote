package com.topjava.CafeVote.web.restaurant;

import com.topjava.CafeVote.error.ExceptionInfoHandler;
import com.topjava.CafeVote.error.NotFoundException;
import com.topjava.CafeVote.model.Meal;
import com.topjava.CafeVote.model.Menu;
import com.topjava.CafeVote.model.Restaurant;
import com.topjava.CafeVote.service.MealService;
import com.topjava.CafeVote.service.MenuService;
import com.topjava.CafeVote.service.RestaurantService;
import com.topjava.CafeVote.to.MenuTo;
import com.topjava.CafeVote.util.JsonUtil;
import com.topjava.CafeVote.util.ToUtil;
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
import static com.topjava.CafeVote.error.ErrorType.*;
import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
                .param("day", "2021-01-10")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TEST_MATCHER.contentJson(REST_2));
    }

    @Test
    void restaurantGetForDayNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_6ID + "/for")
                .param("day", "2021-01-10")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                //.andExpect(errorType(DATA_NOT_FOUND))
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }

    @Test
    void restaurantGetAllForDay() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/for")
                .param("day", "2021-01-10")
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


    /* --- meals --- */

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



    /* --- menus --- */

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
                .param("day", "2021-01-11")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TEST_MATCHER.contentJson(MENU24, MENU26))
                .andDo(print());
    }

    @Test
    void menuGetAllMenusForDay() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/menus/for")
                .param("day", "2021-01-11")
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
                .param("day", "2021-01-10")
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
 /*
    @Test
    void testFullCase() throws Exception {
        //create new restaurant
        ResultActions restaurantAction = mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new Restaurant(null, "The Most Amazing Restaurant"))))
                .andExpect(status().isCreated())
                .andDo(print());
        Restaurant newRestaurant = readFromJson(restaurantAction, Restaurant.class);

        //check
        mockMvc.perform(get(REST_URL + newRestaurant.getId())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, Restaurant.class), newRestaurant));

        //create new Meal
        ResultActions MealAction = mockMvc.perform(post(REST_URL + newRestaurant.getId() + "/meals")
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new Meal(null, "The Most Delicious Rib Eye Steak"))))
                .andExpect(status().isOk())
                .andDo(print());
        Meal newMeal = readFromJson(MealAction, Meal.class);

        //check
        mockMvc.perform(get(REST_URL + newRestaurant.getId() + "/meals")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(Meal.class, newMeal))
                .andDo(print());

        //create new Menu
        ResultActions MenuAction = mockMvc.perform(post(REST_URL + newRestaurant.getId() + "/menus/" + newMeal.getId())
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new Menu(null, null, 100500))))
                .andExpect(status().isOk())
                .andDo(print());
        Menu newMenu = readFromJson(MenuAction, Menu.class);

        //check
        mockMvc.perform(get(REST_URL + newRestaurant.getId())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, Restaurant.class), newRestaurant));

        //voting for new Restaurant with Menu
        ResultActions voteActions = mockMvc.perform(post(UserRestaurantRestController.REST_URL + "/" + newRestaurant.getId() + "/vote")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isCreated())
                .andDo(print());
        Vote newVote = readFromJson(voteActions, Vote.class);

        //check
        mockMvc.perform(get(VoteRestController.REST_URL + "/" + newVote.getId())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, Vote.class), newVote));
    }

    //votes
    @Test
    void testGetAllVotesForTodayForRestaurant() throws Exception {
        mockMvc.perform(get(REST_URL + RES9_ID + "/votes")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VoteTo.class, new ArrayList<>()))
                .andDo(print());
    }

    @Test
    void testGetAllVotesForDayForRestaurant() throws Exception {
        mockMvc.perform(get(REST_URL + RES8_ID + "/votes/for?day=2019-07-03")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VoteTo.class, VOTE_TOS_FOR_20190713_FOR_RES8))
                .andDo(print());
    }

    */
}




