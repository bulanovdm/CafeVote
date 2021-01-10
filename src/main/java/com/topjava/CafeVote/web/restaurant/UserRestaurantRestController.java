package com.topjava.CafeVote.web.restaurant;

import com.topjava.CafeVote.config.security.AuthUser;
import com.topjava.CafeVote.model.Menu;
import com.topjava.CafeVote.model.Restaurant;
import com.topjava.CafeVote.model.Vote;
import com.topjava.CafeVote.service.MealService;
import com.topjava.CafeVote.service.MenuService;
import com.topjava.CafeVote.service.RestaurantService;
import com.topjava.CafeVote.service.VoteService;
import com.topjava.CafeVote.to.VoteTo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantRestController extends AbstractRestaurantRestController {
    public static final String REST_URL = "/restaurants";

    public UserRestaurantRestController(RestaurantService restaurantService, MealService mealService, MenuService menuService, VoteService voteService) {
        super(restaurantService, mealService, menuService, voteService);
    }

    @GetMapping("/{id}")
    public Restaurant getForDay(@PathVariable int id) {
        return super.getForDay(id, LocalDate.now());
    }

    @Override
    @GetMapping
    public List<Restaurant> getAllForToday() {
        return super.getAllForToday();
    }

    @PostMapping("/{id}/vote")
    public ResponseEntity<Vote> vote(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        Vote vote = super.vote(LocalDate.now(), authUser.id(), id);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}/vote")
                .buildAndExpand(vote.getRestaurant().getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(vote);
    }

    @GetMapping("/{id}/menus")
    public List<Menu> getAllMenusForDay(@PathVariable int id) {
        return super.getAllMenusForDayByRestaurantId(id, LocalDate.now());
    }

    @GetMapping("/menus")
    public List<Menu> getAllMenusForDay() {
        return super.getAllMenusForDay(LocalDate.now());
    }

    @GetMapping("/votes")
    public List<VoteTo> getAllVotesForDay(@AuthenticationPrincipal AuthUser authUser) {
        return super.getAllForDateForUser(LocalDate.now(), authUser.id());
    }
}
