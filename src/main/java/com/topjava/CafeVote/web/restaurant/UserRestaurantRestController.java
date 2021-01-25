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
import io.swagger.annotations.Api;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.topjava.CafeVote.util.DateTimeUtil.getCurrentDate;
import static com.topjava.CafeVote.util.DateTimeUtil.getCurrentTime;
import static com.topjava.CafeVote.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = UserRestaurantRestController.USER_RESTAURANTS_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Restaurant Controller")
public class UserRestaurantRestController extends AbstractRestaurantRestController {
    public static final String USER_RESTAURANTS_REST_URL = "/api/restaurants";

    public UserRestaurantRestController(RestaurantService restaurantService, MealService mealService, MenuService menuService, VoteService voteService) {
        super(restaurantService, mealService, menuService, voteService);
    }

    @GetMapping("/{restId}")
    public Restaurant getForDay(@PathVariable int restId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return super.getForDay(restId, date);
    }

    @Override
    @GetMapping
    public List<Restaurant> getAllForToday() {
        return super.getAllForToday();
    }

    @PostMapping("/{restId}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Vote> makeVote(@PathVariable("restId") int restId,
                                         @AuthenticationPrincipal AuthUser authUser) {
        Vote created = super.vote(authUser.id(), restId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{restId}/vote/{id}")
                .buildAndExpand(restId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restId}/vote/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVote(@PathVariable("restId") int restId, @RequestBody Vote vote, @PathVariable("id") int id,
                           @AuthenticationPrincipal AuthUser authUser) {
        assureIdConsistent(vote, id);
        super.updateVote(vote, authUser.id(), restId, getCurrentDate(), getCurrentTime());
    }

    @DeleteMapping("/{restId}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserVote(@PathVariable("restId") int restId, @AuthenticationPrincipal AuthUser authUser) {
        super.deleteVote(authUser.id(), getCurrentDate(), getCurrentTime());
    }

    @GetMapping("/{restId}/menus")
    public List<Menu> getAllMenusForDay(@PathVariable int restId) {
        return super.getAllMenusForDayByRestaurantId(restId, LocalDate.now());
    }

    @GetMapping("/{restId}/vote/{id}")
    public Vote getTodayUserVote(@PathVariable("restId") int restId, @PathVariable("id") int id, @AuthenticationPrincipal AuthUser authUser) {
        return super.getVote(authUser.id(), getCurrentDate());
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
