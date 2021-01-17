package com.topjava.CafeVote.web.vote;

import com.topjava.CafeVote.model.Vote;
import com.topjava.CafeVote.service.VoteService;
import com.topjava.CafeVote.to.VoteTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = VoteRestController.ADMIN_VOTES_REST_CONTROLLER, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteRestController {
    public static final String ADMIN_VOTES_REST_CONTROLLER = "/admin/votes";
    private final VoteService voteService;

    @Autowired
    public VoteRestController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping()
    public List<VoteTo> getAllForToday() {
        log.info("get all votes for today");
        return voteService.getAllForDate(LocalDate.now());
    }

    @GetMapping("/for")
    public List<VoteTo> getAllForDate(@RequestParam LocalDate day) {
        log.info("get all votes for date");
        return voteService.getAllForDate(day);
    }

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get vote with id={}", id);
        return voteService.get(id);
    }
}
