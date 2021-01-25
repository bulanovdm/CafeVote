package com.topjava.CafeVote.repository;

import com.topjava.CafeVote.model.Vote;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id=:userId AND v.voteDate=:day")
    int delete(@Param("userId") int userId, @Param("day") LocalDate day);

    @Query("SELECT DISTINCT v FROM Vote v JOIN FETCH v.user JOIN FETCH v.restaurant WHERE v.voteDate=:date ORDER BY v.id")
    List<Vote> getAllForDate(@Param("date") LocalDate date);

    @Query("SELECT DISTINCT v FROM Vote v JOIN FETCH v.user JOIN FETCH v.restaurant r JOIN FETCH r.menus m JOIN FETCH m.meal WHERE m.menuDate=v.voteDate AND v.id=:id")
    Vote get(@Param("id") int id);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:id AND v.voteDate=:date")
    Optional<Vote> getByUserIdAndVoteDate(@Param("id") int userId, @Param("date") LocalDate voteDay);

    @Query("SELECT DISTINCT v FROM Vote v JOIN FETCH v.user u JOIN FETCH v.restaurant WHERE v.voteDate=:date AND u.id=:userId ORDER BY u.email, v.id")
    List<Vote> getAllForDateForUser(@Param("date") LocalDate date, @Param("userId") int userId);

    @Query("SELECT DISTINCT v FROM Vote v JOIN FETCH v.user JOIN FETCH v.restaurant r WHERE v.voteDate=:date AND r.id=:restaurantId ORDER BY r.name, v.id")
    List<Vote> getAllForDateForRestaurant(@Param("date") LocalDate date, @Param("restaurantId") int restaurantId);
}
