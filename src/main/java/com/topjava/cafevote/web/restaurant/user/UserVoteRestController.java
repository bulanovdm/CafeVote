package com.topjava.cafevote.web.restaurant.user;

import com.topjava.cafevote.config.security.AuthUser;
import com.topjava.cafevote.model.Vote;
import com.topjava.cafevote.service.MealService;
import com.topjava.cafevote.service.MenuService;
import com.topjava.cafevote.service.RestaurantService;
import com.topjava.cafevote.service.VoteService;
import com.topjava.cafevote.web.restaurant.AbstractRestaurantRestController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;

import static com.topjava.cafevote.util.DateTimeUtil.getCurrentDate;

@Slf4j
@RestController
@RequestMapping(value = AbstractRestaurantRestController.USER_RESTAURANTS_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "User Vote Controller")
public class UserVoteRestController extends AbstractRestaurantRestController {

    public UserVoteRestController(RestaurantService restaurantService, MealService mealService, MenuService menuService, VoteService voteService) {
        super(restaurantService, mealService, menuService, voteService);
    }

    @PostMapping("/{restId}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Vote> makeVote(@PathVariable("restId") int restId,
                                         @ApiIgnore @AuthenticationPrincipal AuthUser authUser) {
        log.info("user with id={} voted for restaurant with id={}", authUser.getUsername(), restId);
        Vote created = voteService.create(authUser.id(), restId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{restId}/vote/{id}")
                .buildAndExpand(restId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restId}/vote/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVote(@PathVariable("restId") int restId, @RequestBody Vote vote, @PathVariable("id") int id,
                           @ApiIgnore @AuthenticationPrincipal AuthUser authUser) {
        log.info("user with id={} update vote for restaurant with id={}", authUser.id(), restId);
        voteService.update(authUser.id(), restId);
    }

    @DeleteMapping("/{restId}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserVote(@PathVariable("restId") int restId, @ApiIgnore @AuthenticationPrincipal AuthUser authUser) {
        log.info("delete vote by user with id={} by date={}", authUser.id(), getCurrentDate());
        voteService.delete(authUser.id());
    }

    @GetMapping("/{restId}/vote/{id}")
    public Vote getTodayUserVote(@PathVariable("restId") int restId, @PathVariable("id") int id, @ApiIgnore @AuthenticationPrincipal AuthUser authUser) {
        log.info("get vote of user id={} for day {}", authUser.id(), getCurrentDate());
        return voteService.getVote(authUser.id(), getCurrentDate());
    }
}
