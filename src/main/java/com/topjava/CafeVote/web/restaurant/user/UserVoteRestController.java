package com.topjava.CafeVote.web.restaurant.user;

import com.topjava.CafeVote.config.security.AuthUser;
import com.topjava.CafeVote.model.Vote;
import com.topjava.CafeVote.service.MealService;
import com.topjava.CafeVote.service.MenuService;
import com.topjava.CafeVote.service.RestaurantService;
import com.topjava.CafeVote.service.VoteService;
import com.topjava.CafeVote.to.VoteTo;
import com.topjava.CafeVote.web.restaurant.AbstractRestaurantRestController;
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
import java.util.List;

import static com.topjava.CafeVote.util.DateTimeUtil.getCurrentDate;
import static com.topjava.CafeVote.util.DateTimeUtil.getCurrentTime;
import static com.topjava.CafeVote.util.ValidationUtil.assureIdConsistent;

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
        assureIdConsistent(vote, id);
        voteService.update(vote, authUser.id(), restId, getCurrentDate(), getCurrentTime());
    }

    @DeleteMapping("/{restId}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserVote(@PathVariable("restId") int restId, @ApiIgnore @AuthenticationPrincipal AuthUser authUser) {
        log.info("delete vote by user with id={} by date={}", authUser.id(), getCurrentDate());
        voteService.delete(authUser.id(), getCurrentDate(), getCurrentTime());
    }

    @GetMapping("/{restId}/vote/{id}")
    public Vote getTodayUserVote(@PathVariable("restId") int restId, @PathVariable("id") int id, @ApiIgnore @AuthenticationPrincipal AuthUser authUser) {
        log.info("get vote of user id={} for day {}", authUser.id(), getCurrentDate());
        return voteService.getVote(authUser.id(), getCurrentDate());
    }

    @GetMapping("/votes")
    public List<VoteTo> getAllVotesForDay(@ApiIgnore @AuthenticationPrincipal AuthUser authUser) {
        log.info("user with id={} get all his votes for current date", authUser.id());
        return voteService.getAllForDateForUser(getCurrentDate(), authUser.id());
    }
}
