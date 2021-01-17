package com.topjava.CafeVote.service;

import com.topjava.CafeVote.error.IllegalRequestDataException;
import com.topjava.CafeVote.model.Restaurant;
import com.topjava.CafeVote.model.User;
import com.topjava.CafeVote.model.Vote;
import com.topjava.CafeVote.repository.RestaurantRepository;
import com.topjava.CafeVote.repository.UserRepository;
import com.topjava.CafeVote.repository.VoteRepository;
import com.topjava.CafeVote.to.VoteTo;
import com.topjava.CafeVote.util.ToUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


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

    public List<VoteTo> getAllForDate(LocalDate date) {
        return ToUtil.votesAsToList(voteRepository.getAllForDate(date));
    }

    public Vote get(int id) {
        return voteRepository.get(id);
    }

    public Optional<Vote> getByUserIdAndVotingDate(int userId, LocalDate votingDay) {
        return voteRepository.findByUserIdAndVoteDate(userId, votingDay);
    }

    @SneakyThrows
    @Transactional
    public Vote vote(LocalDate date, int userId, int restaurantId) {

        Optional<Vote> optionalVote = voteRepository.findByUserIdAndVoteDate(userId, date);
        User user = userRepository.getOne(userId);
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);

        if (optionalVote.isEmpty()) {
            return voteRepository.save(new Vote(user, restaurant, date));
        }

        Vote vote;
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            vote = voteRepository.save(new Vote(optionalVote.get().getId(), date, user, restaurant));
        } else {
            throw new IllegalRequestDataException(String.format("User %s %s already voted", user.getLastName(), user.getFirstName()));
        }
        return vote;
    }

    public List<VoteTo> getAllForDateForUser(LocalDate date, int userId) {
        return ToUtil.votesAsToList(voteRepository.getAllForDateForUser(date, userId));
    }

    public List<VoteTo> getAllForDateForRestaurant(LocalDate date, int restaurantId) {
        return ToUtil.votesAsToList(voteRepository.getAllForDateForRestaurant(date, restaurantId));
    }
}
