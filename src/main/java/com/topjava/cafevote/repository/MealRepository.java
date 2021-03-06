package com.topjava.cafevote.repository;

import com.topjava.cafevote.model.Meal;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional(readOnly = true)
public interface MealRepository extends BaseRepository<Meal> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Query("SELECT m FROM Meal m")
    List<Meal> getAll();

    @Query("SELECT m FROM Meal m WHERE m.restaurant.id=:restaurantId")
    List<Meal> getAllByRestaurantId(@Param("restaurantId") int restaurantId);

    @Query("SELECT m FROM Meal m WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    Meal get(@Param("id") int id, @Param("restaurantId") int restaurantId);
}
