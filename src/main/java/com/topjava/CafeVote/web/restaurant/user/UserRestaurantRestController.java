package com.topjava.CafeVote.web.restaurant.user;

import com.topjava.CafeVote.model.Restaurant;
import com.topjava.CafeVote.service.MealService;
import com.topjava.CafeVote.service.MenuService;
import com.topjava.CafeVote.service.RestaurantService;
import com.topjava.CafeVote.service.VoteService;
import com.topjava.CafeVote.web.restaurant.AbstractRestaurantRestController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = AbstractRestaurantRestController.USER_RESTAURANTS_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "User Restaurant Controller")
public class UserRestaurantRestController extends AbstractRestaurantRestController {

    public UserRestaurantRestController(RestaurantService restaurantService, MealService mealService, MenuService menuService, VoteService voteService) {
        super(restaurantService, mealService, menuService, voteService);
    }

    @GetMapping("/{restId}")
    public Restaurant getForDay(@PathVariable int restId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get restaurant with id={} for day={}", restId, date);
        return restaurantService.getForDay(restId, date);
    }

    @GetMapping
    public List<Restaurant> getAllForToday() {
        log.info("get all restaurants for today");
        return restaurantService.getAllForToday();
    }
}
