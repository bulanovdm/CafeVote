package com.topjava.CafeVote.service;

import com.topjava.CafeVote.error.IllegalRequestDataException;
import com.topjava.CafeVote.model.Vote;
import com.topjava.CafeVote.repository.RestaurantRepository;
import com.topjava.CafeVote.repository.UserRepository;
import com.topjava.CafeVote.repository.VoteRepository;
import com.topjava.CafeVote.to.VoteTo;
import com.topjava.CafeVote.util.ToUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.topjava.CafeVote.util.DateTimeUtil.getCurrentDate;
import static com.topjava.CafeVote.util.ValidationUtil.checkNotFoundWithIdOptional;
import static com.topjava.CafeVote.util.ValidationUtil.isNotExpired;


@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Vote save(Vote vote, int userId, int restaurantId) {
        if (vote.getVoteDate() == null) vote.setVoteDate(getCurrentDate());
        vote.setUser(userRepository.getOne(userId));
        vote.setRestaurant(restaurantRepository.getOne(restaurantId));
        return voteRepository.save(vote);
    }

    public Vote create(int userId, int restId) {
        return save(new Vote(), userId, restId);
    }

    public void update(Vote vote, int userId, int restId, LocalDate day, LocalTime voteTime) {
        Assert.notNull(vote, "vote must not be null");
        checkNotFoundWithIdOptional(voteRepository.getByUserIdAndVoteDate(userId, day), vote.id());
        if (isNotExpired(voteTime)) {
            save(vote, userId, restId);
        } else {
            throw new IllegalRequestDataException("Time to vote is already expired");
        }
    }

    public Vote getVote(int userId, LocalDate day) {
        return checkNotFoundWithIdOptional(voteRepository.getByUserIdAndVoteDate(userId, day), "Vote not found");
    }


    public boolean delete(int userId, LocalDate day, LocalTime voteTime) {
        Optional<Vote> oldVote = voteRepository.getByUserIdAndVoteDate(userId, day);
        if (oldVote.isPresent() && isNotExpired(voteTime)) {
            return voteRepository.delete(userId, day) != 0;
        } else {
            throw new IllegalRequestDataException("Vote delete is expired");
        }
    }

    public List<VoteTo> getAllForDate(LocalDate date) {
        return ToUtil.votesAsToList(voteRepository.getAllForDate(date));
    }

    public List<VoteTo> getAllForDateForUser(LocalDate date, int userId) {
        return ToUtil.votesAsToList(voteRepository.getAllForDateForUser(date, userId));
    }

    public List<VoteTo> getAllForDateForRestaurant(LocalDate date, int restaurantId) {
        return ToUtil.votesAsToList(voteRepository.getAllForDateForRestaurant(date, restaurantId));
    }
}
