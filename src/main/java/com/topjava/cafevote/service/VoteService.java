package com.topjava.cafevote.service;

import com.topjava.cafevote.error.IllegalRequestDataException;
import com.topjava.cafevote.error.NotFoundException;
import com.topjava.cafevote.model.Vote;
import com.topjava.cafevote.repository.RestaurantRepository;
import com.topjava.cafevote.repository.UserRepository;
import com.topjava.cafevote.repository.VoteRepository;
import com.topjava.cafevote.to.VoteTo;
import com.topjava.cafevote.util.ToUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.topjava.cafevote.util.DateTimeUtil.getCurrentDate;
import static com.topjava.cafevote.util.DateTimeUtil.getCurrentTime;
import static com.topjava.cafevote.util.ValidationUtil.checkNotFoundWithIdOptional;
import static com.topjava.cafevote.util.ValidationUtil.isNotExpired;


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
    public Vote create(int userId, int restaurantId) {
        var vote = new Vote();
        if (vote.getVoteDate() == null) vote.setVoteDate(getCurrentDate());
        vote.setUser(userRepository.getOne(userId));
        vote.setRestaurant(restaurantRepository.getOne(restaurantId));
        return voteRepository.save(vote);
    }

    public void update(int userId, int restId) {
        if (isNotExpired(getCurrentTime())) {
            var voteToUpdate = voteRepository.getByUserIdAndVoteDate(userId, getCurrentDate())
                    .orElseThrow(() -> new NotFoundException("Vote for today not found"));
            voteToUpdate.setRestaurant(restaurantRepository.getOne(restId));
            voteRepository.save(voteToUpdate);
        } else {
            throw new IllegalRequestDataException("Time to vote is already expired");
        }
    }

    public Vote getVote(int userId, LocalDate day) {
        return checkNotFoundWithIdOptional(voteRepository.getByUserIdAndVoteDate(userId, day), "Vote not found");
    }


    public boolean delete(int userId) {
        Optional<Vote> oldVote = voteRepository.getByUserIdAndVoteDate(userId, getCurrentDate());
        if (oldVote.isPresent() && isNotExpired(getCurrentTime())) {
            return voteRepository.delete(userId, getCurrentDate()) != 0;
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
