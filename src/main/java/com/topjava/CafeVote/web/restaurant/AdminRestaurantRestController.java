package com.topjava.CafeVote.web.restaurant;

import com.topjava.CafeVote.View;
import com.topjava.CafeVote.model.Meal;
import com.topjava.CafeVote.model.Menu;
import com.topjava.CafeVote.model.Restaurant;
import com.topjava.CafeVote.service.MealService;
import com.topjava.CafeVote.service.MenuService;
import com.topjava.CafeVote.service.RestaurantService;
import com.topjava.CafeVote.service.VoteService;
import com.topjava.CafeVote.to.MealTo;
import com.topjava.CafeVote.to.MenuTo;
import com.topjava.CafeVote.to.RestaurantTo;
import com.topjava.CafeVote.to.VoteTo;
import io.swagger.annotations.Api;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminRestaurantRestController.ADMIN_RESTAURANTS_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Admin Restaurant Controller")
public class AdminRestaurantRestController extends AbstractRestaurantRestController {
    public static final String ADMIN_RESTAURANTS_REST_URL = "/api/admin/restaurants";

    public AdminRestaurantRestController(RestaurantService restaurantService, MealService mealService, MenuService menuService, VoteService voteService) {
        super(restaurantService, mealService, menuService, voteService);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Validated(View.Web.class) @RequestBody Restaurant restaurant) {
        Restaurant created = super.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ADMIN_RESTAURANTS_REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    /* --- restaurants --- */
    @Override
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody Restaurant restaurant) {
        super.update(restaurant);
    }

    @Override
    @GetMapping
    public List<RestaurantTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping("/{id}/for")
    public Restaurant getForDay(@PathVariable int id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        return super.getForDay(id, day);
    }

    @Override
    @GetMapping("/for")
    public List<Restaurant> getAllForDay(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        return super.getAllForDay(day);
    }

    /* --- meals --- */
    @Override
    @PostMapping(value = "/{restaurantId}/meals", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Meal createMeal(@Validated(View.Web.class) @RequestBody Meal Meal, @PathVariable int restaurantId) {
        return super.createMeal(Meal, restaurantId);
    }

    @Override
    @PutMapping(value = "/{restaurantId}/meals", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMeal(@Validated(View.Web.class) @RequestBody Meal Meal, @PathVariable int restaurantId) {
        super.updateMeal(Meal, restaurantId);
    }

    @Override
    @GetMapping("/{restaurantId}/meals")
    public List<Meal> getAllMealsForRestaurant(@PathVariable int restaurantId) {
        return super.getAllMealsForRestaurant(restaurantId);
    }

    @Override
    @GetMapping("/meals")
    public List<MealTo> getAllMeals() {
        return super.getAllMeals();
    }

    @Override
    @GetMapping("/{restaurantId}/meals/{id}")
    public Meal getMeal(@PathVariable int id, @PathVariable int restaurantId) {
        return super.getMeal(id, restaurantId);
    }

    @Override
    @DeleteMapping("/{restaurantId}/meals/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeal(@PathVariable int id, @PathVariable int restaurantId) {
        super.deleteMeal(id, restaurantId);
    }

    /* --- menus --- */
    @Override
    @PostMapping(value = "/{restaurantId}/menus/{MealId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Menu createMenu(@Validated(View.Web.class) @RequestBody Menu Menu,
                           @PathVariable int restaurantId,
                           @PathVariable int MealId) {
        return super.createMenu(Menu, restaurantId, MealId);
    }

    @Override
    @PutMapping(value = "/{restaurantId}/menus/{MealId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMenu(@Validated(View.Web.class) @RequestBody Menu Menu,
                           @PathVariable int restaurantId,
                           @PathVariable int MealId) {
        super.updateMenu(Menu, restaurantId, MealId);
    }

    @Override
    @GetMapping("/{restaurantId}/menus")
    public List<Menu> getAllMenusForRestaurant(@PathVariable int restaurantId) {
        return super.getAllMenusForRestaurant(restaurantId);
    }

    @Override
    @GetMapping("/menus")
    public List<MenuTo> getAllMenus() {
        return super.getAllMenus();
    }

    @Override
    @GetMapping("/{restaurantId}/menus/for")
    public List<Menu> getAllMenusForDayByRestaurantId(@PathVariable int restaurantId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        return super.getAllMenusForDayByRestaurantId(restaurantId, day);
    }

    @Override
    @GetMapping("/menus/for")
    public List<Menu> getAllMenusForDay(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        return super.getAllMenusForDay(day);
    }

    @Override
    @GetMapping("/{restaurantId}/menus/{id}")
    public Menu getMenu(@PathVariable int id, @PathVariable int restaurantId) {
        return super.getMenu(id, restaurantId);
    }

    @Override
    @DeleteMapping("/{restaurantId}/menus/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenu(@PathVariable int id, @PathVariable int restaurantId) {
        super.deleteMenu(id, restaurantId);
    }

    @Override
    @DeleteMapping("/{restaurantId}/menus/for")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllMenusForDay(@PathVariable int restaurantId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        super.deleteAllMenusForDay(restaurantId, day);
    }

    /* --- votes --- */
    @Override
    @GetMapping("/{restaurantId}/votes/for")
    public List<VoteTo> getAllForDateForRestaurant(@RequestParam LocalDate day, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) int restaurantId) {
        return super.getAllForDateForRestaurant(day, restaurantId);
    }

    @GetMapping("/{restaurantId}/votes")
    public List<VoteTo> getAllForTodayForRestaurant(@PathVariable int restaurantId) {
        return super.getAllForDateForRestaurant(LocalDate.now(), restaurantId);
    }
}
