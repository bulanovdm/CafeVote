package com.topjava.cafevote.web.restaurant.user;

import com.topjava.cafevote.model.Menu;
import com.topjava.cafevote.service.MealService;
import com.topjava.cafevote.service.MenuService;
import com.topjava.cafevote.service.RestaurantService;
import com.topjava.cafevote.service.VoteService;
import com.topjava.cafevote.web.restaurant.AbstractRestaurantRestController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.topjava.cafevote.util.DateTimeUtil.getCurrentDate;

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
        return menuService.getAllForByDateRestaurantId(restId, getCurrentDate());
    }

    @GetMapping("/menus")
    public List<Menu> getAllMenusForDay() {
        log.info("get all Menus for day");
        return menuService.getAllForDate(getCurrentDate());
    }
}
