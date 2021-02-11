package com.topjava.CafeVote.web.restaurant.admin;

import com.topjava.CafeVote.View;
import com.topjava.CafeVote.model.Meal;
import com.topjava.CafeVote.service.MealService;
import com.topjava.CafeVote.service.MenuService;
import com.topjava.CafeVote.service.RestaurantService;
import com.topjava.CafeVote.service.VoteService;
import com.topjava.CafeVote.to.MealTo;
import com.topjava.CafeVote.web.restaurant.AbstractRestaurantRestController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.topjava.CafeVote.util.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(value = AbstractRestaurantRestController.ADMIN_RESTAURANTS_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Admin Meal Controller")
public class AdminMealRestController extends AbstractRestaurantRestController {

    public AdminMealRestController(RestaurantService restaurantService, MealService mealService, MenuService menuService, VoteService voteService) {
        super(restaurantService, mealService, menuService, voteService);
    }

    /* --- meals --- */

    @PostMapping(value = "/{restaurantId}/meals", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Meal createMeal(@Validated(View.Web.class) @RequestBody Meal meal, @PathVariable int restaurantId) {
        log.info("create Meal with name={} for restaurant with id={}", meal.getName(), restaurantId);
        checkNew(meal);
        return mealService.create(meal, restaurantId);
    }

    @PutMapping(value = "/{restaurantId}/meals", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMeal(@Validated(View.Web.class) @RequestBody Meal meal, @PathVariable int restaurantId) {
        log.info("update Meal with id={} for restaurant with id={}", meal.getId(), restaurantId);
        mealService.update(meal, restaurantId);
    }

    @GetMapping("/{restaurantId}/meals")
    public List<Meal> getAllMealsForRestaurant(@PathVariable int restaurantId) {
        log.info("get all Meals for restaurant with id={}", restaurantId);
        return mealService.getAll(restaurantId);
    }

    @GetMapping("/meals")
    public List<MealTo> getAllMeals() {
        log.info("get all Meals");
        return mealService.getAll();
    }

    @GetMapping("/{restaurantId}/meals/{id}")
    public Meal getMeal(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("get Meal with id={} for restaurant with id={}", id, restaurantId);
        return mealService.get(id, restaurantId);
    }

    @DeleteMapping("/{restaurantId}/meals/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeal(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("delete Meal with id={} for restaurant with id={}", id, restaurantId);
        mealService.delete(id, restaurantId);
    }
}
