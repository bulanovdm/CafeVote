package com.topjava.CafeVote.web.restaurant.user;

import com.topjava.CafeVote.model.Menu;
import com.topjava.CafeVote.service.MealService;
import com.topjava.CafeVote.service.MenuService;
import com.topjava.CafeVote.service.RestaurantService;
import com.topjava.CafeVote.service.VoteService;
import com.topjava.CafeVote.web.restaurant.AbstractRestaurantRestController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = AbstractRestaurantRestController.USER_RESTAURANTS_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "User Restaurant Controller")
public class UserMenuRestController extends AbstractRestaurantRestController {

    public UserMenuRestController(RestaurantService restaurantService, MealService mealService, MenuService menuService, VoteService voteService) {
        super(restaurantService, mealService, menuService, voteService);
    }

    @GetMapping("/{restId}/menus")
    public List<Menu> getAllMenusForDay(@PathVariable int restId) {
        log.info("get all Menus for restaurant with id={} for day", restId);
        return menuService.getAllForByDateRestaurantId(restId, LocalDate.now());
    }

    @GetMapping("/menus")
    public List<Menu> getAllMenusForDay() {
        log.info("get all Menus for day");
        return menuService.getAllForDate(LocalDate.now());
    }
}
