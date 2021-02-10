package com.topjava.CafeVote.repository;

import com.topjava.CafeVote.model.Restaurant;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT r FROM Restaurant r")
    List<Restaurant> getAll();

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menus m JOIN FETCH m.meal WHERE r.id=:restaurantId")
    Restaurant getFullById(@Param("restaurantId") Integer restaurantId);

    @Query("SELECT DISTINCT r FROM Restaurant r JOIN FETCH r.menus m JOIN FETCH m.meal WHERE m.menuDate=:day ORDER BY r.name")
    List<Restaurant> getAllForDay(@Param("day") LocalDate day);

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menus m JOIN FETCH m.meal WHERE r.id=:restaurantId AND m.menuDate=:day")
    Restaurant getByIdForDay(@Param("restaurantId") Integer restaurantId, @Param("day") LocalDate day);
}
