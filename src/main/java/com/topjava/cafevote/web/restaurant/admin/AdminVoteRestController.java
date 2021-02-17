package com.topjava.cafevote.web.restaurant.admin;

import com.topjava.cafevote.service.MealService;
import com.topjava.cafevote.service.MenuService;
import com.topjava.cafevote.service.RestaurantService;
import com.topjava.cafevote.service.VoteService;
import com.topjava.cafevote.to.VoteTo;
import com.topjava.cafevote.web.restaurant.AbstractRestaurantRestController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.topjava.cafevote.util.DateTimeUtil.getCurrentDate;

@Slf4j
@RestController
@RequestMapping(value = AbstractRestaurantRestController.ADMIN_RESTAURANTS_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Admin Vote Controller")
public class AdminVoteRestController extends AbstractRestaurantRestController {

    public AdminVoteRestController(RestaurantService restaurantService, MealService mealService, MenuService menuService, VoteService voteService) {
        super(restaurantService, mealService, menuService, voteService);
    }

    /* --- votes --- */

    @GetMapping("/{restaurantId}/votes/for")
    public List<VoteTo> getAllForDateForRestaurant(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @PathVariable int restaurantId) {
        log.info("get all votes for date={} for restaurant with id={}", date, restaurantId);
        return voteService.getAllForDateForRestaurant(date, restaurantId);
    }

    @GetMapping("/{restaurantId}/votes")
    public List<VoteTo> getAllForTodayForRestaurant(@PathVariable int restaurantId) {
        log.info("get all votes for today for restaurant with id={}", restaurantId);
        return voteService.getAllForDateForRestaurant(getCurrentDate(), restaurantId);
    }
}
