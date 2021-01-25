package com.topjava.CafeVote.web.restaurant;


import com.topjava.CafeVote.model.Meal;
import com.topjava.CafeVote.model.Menu;
import com.topjava.CafeVote.model.Restaurant;
import com.topjava.CafeVote.model.Vote;
import com.topjava.CafeVote.service.MealService;
import com.topjava.CafeVote.service.MenuService;
import com.topjava.CafeVote.service.RestaurantService;
import com.topjava.CafeVote.service.VoteService;
import com.topjava.CafeVote.to.MealTo;
import com.topjava.CafeVote.to.MenuTo;
import com.topjava.CafeVote.to.RestaurantTo;
import com.topjava.CafeVote.to.VoteTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
public abstract class AbstractRestaurantRestController {

    private final RestaurantService restaurantService;
    private final MealService mealService;
    private final MenuService menuService;
    private final VoteService voteService;

    @Autowired
    public AbstractRestaurantRestController(RestaurantService restaurantService, MealService mealService,
                                            MenuService menuService, VoteService voteService) {
        this.restaurantService = restaurantService;
        this.mealService = mealService;
        this.menuService = menuService;
        this.voteService = voteService;
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("create restaurant with name={}", restaurant.getName());
        return restaurantService.create(restaurant);
    }

    public void updateVote(Restaurant restaurant) {
        log.info("update restaurant with id={}", restaurant.getId());
        restaurantService.update(restaurant);
    }

    public List<RestaurantTo> getAll() {
        log.info("get all restaurants");
        return restaurantService.getAll();
    }

    public Restaurant get(int id) {
        log.info("get restaurant with id={}", id);
        return restaurantService.get(id);
    }

    public void delete(int id) {
        log.info("delete restaurant with id={}", id);
        restaurantService.delete(id);
    }

    public Restaurant getForDay(int id, LocalDate localDate) {
        log.info("get restaurant with id={} for day", id);
        return restaurantService.getForDay(id, localDate);
    }

    public List<Restaurant> getAllForDay(LocalDate localDate) {
        log.info("get all restaurants for day {}", localDate);
        return restaurantService.getAllForDay(localDate);
    }

    public List<Restaurant> getAllForToday() {
        log.info("get all restaurants for today");
        return restaurantService.getAllForToday();
    }

    //vote
    public Vote vote(int userId, int restaurantId) {
        log.info("user with id={} voted for restaurant with id={}", userId, restaurantId);
        return voteService.create(userId, restaurantId);
    }

    public void updateVote(Vote vote, int userId, int restId, LocalDate day, LocalTime voteTime) {
        log.info("user with id={} update vote for restaurant with id={}", userId, restId);
        voteService.update(vote, userId, restId, day, voteTime);
    }

    public Vote getVote(int userId, LocalDate day) {
        log.info("get vote of user id={} for day {}", userId, day);
        return voteService.getVote(userId, day);
    }

    public void deleteVote(int id, LocalDate day, LocalTime time) {
        log.info("delete vote with id={}", id);
        voteService.delete(id, day, time);
    }

    //votes
    public List<VoteTo> getAllForDateForUser(LocalDate date, int userId) {
        log.info("user with id={} get all his votes for current date", userId);
        return voteService.getAllForDateForUser(date, userId);
    }

    public List<VoteTo> getAllForDateForRestaurant(LocalDate date, int restaurantId) {
        log.info("get all votes for date for restaurant with id={}", restaurantId);
        return voteService.getAllForDateForRestaurant(date, restaurantId);
    }

    //Meals
    public Meal createMeal(Meal meal, int restaurantId) {
        log.info("create Meal with name={} for restaurant with id={}", meal.getName(), restaurantId);
        return mealService.create(meal, restaurantId);
    }

    public void updateMeal(Meal meal, int restaurantId) {
        log.info("update Meal with id={} for restaurant with id={}", meal.getId(), restaurantId);
        mealService.update(meal, restaurantId);
    }

    public List<Meal> getAllMealsForRestaurant(int restaurantId) {
        log.info("get all Meals for restaurant with id={}", restaurantId);
        return mealService.getAll(restaurantId);
    }

    public List<MealTo> getAllMeals() {
        log.info("get all Meals");
        return mealService.getAll();
    }

    public Meal getMeal(int id, int restaurantId) {
        log.info("get Meal with id={} for restaurant with id={}", id, restaurantId);
        return mealService.get(id, restaurantId);
    }

    public void deleteMeal(int id, int restaurantId) {
        log.info("delete Meal with id={} for restaurant with id={}", id, restaurantId);
        mealService.delete(id, restaurantId);
    }

    //Menus
    public Menu createMenu(Menu Menu, int restaurantId, int MealId) {
        log.info("create Menu for restaurant with id={} and Meal with id={}", restaurantId, MealId);
        return menuService.create(Menu, restaurantId, MealId);
    }

    public void updateMenu(Menu Menu, int restaurantId, int MealId) {
        log.info("update Menu with id={} for restaurant with id={} and Meal with id={}", Menu.getId(), restaurantId, MealId);
        menuService.update(Menu, restaurantId, MealId);
    }

    public List<Menu> getAllMenusForRestaurant(int restaurantId) {
        log.info("get all Menus for restaurant with id={}", restaurantId);
        return menuService.getAllForRestaurantId(restaurantId);
    }

    public List<MenuTo> getAllMenus() {
        log.info("get all Menus");
        return menuService.getAll();
    }

    public List<Menu> getAllMenusForDayByRestaurantId(int restaurantId, LocalDate date) {
        log.info("get all Menus for restaurant with id={} for day", restaurantId);
        return menuService.getAllForByDateRestaurantId(restaurantId, date);
    }

    public List<Menu> getAllMenusForDay(LocalDate date) {
        log.info("get all Menus for day");
        return menuService.getAllForDate(date);
    }

    public Menu getMenu(int id, int restaurantId) {
        log.info("get Menu with id={} for restaurant with id={}", id, restaurantId);
        return menuService.get(id, restaurantId);
    }

    public void deleteMenu(int id, int restaurantId) {
        log.info("delete Menu with id={} for restaurant with id={}", id, restaurantId);
        menuService.delete(id, restaurantId);
    }

    public void deleteAllMenusForDay(int restaurantId, LocalDate date) {
        log.info("delete all Menus for restaurant with id={}", restaurantId);
        menuService.deleteAllForDate(restaurantId, date);
    }
}

