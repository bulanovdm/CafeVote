package com.topjava.cafevote.web.restaurant.admin;

import com.topjava.cafevote.View;
import com.topjava.cafevote.model.Menu;
import com.topjava.cafevote.service.MealService;
import com.topjava.cafevote.service.MenuService;
import com.topjava.cafevote.service.RestaurantService;
import com.topjava.cafevote.service.VoteService;
import com.topjava.cafevote.to.MenuTo;
import com.topjava.cafevote.web.restaurant.AbstractRestaurantRestController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.topjava.cafevote.util.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(value = AbstractRestaurantRestController.ADMIN_RESTAURANTS_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Admin Menu Controller")
public class AdminMenuRestController extends AbstractRestaurantRestController {

    public AdminMenuRestController(RestaurantService restaurantService, MealService mealService, MenuService menuService, VoteService voteService) {
        super(restaurantService, mealService, menuService, voteService);
    }

    /* --- menus --- */

    @PostMapping(value = "/{restaurantId}/menus/{mealId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Menu createMenuStance(@Validated(View.Web.class) @RequestBody Menu menu, @PathVariable int restaurantId, @PathVariable int mealId) {
        log.info("create Menu for restaurant with id={} and Meal with id={}", restaurantId, mealId);
        checkNew(menu);
        return menuService.create(menu, restaurantId, mealId);
    }

    @PutMapping(value = "/{restaurantId}/menus/{mealId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMenuStance(@Validated(View.Web.class) @RequestBody Menu menu, @PathVariable int restaurantId, @PathVariable int mealId) {
        log.info("update Menu with id={} for restaurant with id={} and Meal with id={}", menu.getId(), restaurantId, mealId);
        menuService.update(menu, restaurantId, mealId);
    }

    @GetMapping("/{restaurantId}/menus")
    public List<Menu> getAllMenusForRestaurant(@PathVariable int restaurantId) {
        log.info("get all Menus for restaurant with id={}", restaurantId);
        return menuService.getAllForRestaurantId(restaurantId);
    }

    @GetMapping("/menus")
    public List<MenuTo> getAllMenus() {
        log.info("get all Menus");
        return menuService.getAll();
    }

    @GetMapping("/{restaurantId}/menus/for")
    public List<Menu> getAllMenusForDayByRestaurantId(@PathVariable int restaurantId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get all Menus for restaurant with id={} for day", restaurantId);
        return menuService.getAllForByDateRestaurantId(restaurantId, date);
    }

    @GetMapping("/menus/for")
    public List<Menu> getAllMenusForDay(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get all Menus for day");
        return menuService.getAllForDate(date);
    }

    @GetMapping("/{restaurantId}/menus/{id}")
    public Menu getMenu(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("get Menu with id={} for restaurant with id={}", id, restaurantId);
        return menuService.get(id, restaurantId);
    }

    @DeleteMapping("/{restaurantId}/menus/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenu(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("delete Menu with id={} for restaurant with id={}", id, restaurantId);
        menuService.delete(id, restaurantId);
    }

    @DeleteMapping("/{restaurantId}/menus/for")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllMenusForDay(@PathVariable int restaurantId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("delete all Menus for restaurant with id={}", restaurantId);
        menuService.deleteAllForDate(restaurantId, date);
    }
}
