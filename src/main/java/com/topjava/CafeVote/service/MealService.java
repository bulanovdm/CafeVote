package com.topjava.CafeVote.service;

import com.topjava.CafeVote.error.NotFoundException;
import com.topjava.CafeVote.model.Meal;
import com.topjava.CafeVote.repository.MealRepository;
import com.topjava.CafeVote.to.MealTo;
import com.topjava.CafeVote.util.ToUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.topjava.CafeVote.util.ValidationUtil.checkNew;
import static com.topjava.CafeVote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final RestaurantService restaurantService;

    @Autowired
    public MealService(MealRepository MealRepository, RestaurantService restaurantService) {
        this.mealRepository = MealRepository;
        this.restaurantService = restaurantService;
    }

    @Transactional
    public Meal create(Meal meal, int restaurantId) {
        Assert.notNull(meal, "meal must not be null");
        checkNew(meal);
        meal.setRestaurant(restaurantService.get(restaurantId));
        return mealRepository.save(meal);
    }

    @Transactional
    public void update(Meal meal, int restaurantId) {
        Assert.notNull(meal, "meal must not be null");
        Meal loadedMeal = mealRepository.get(meal.id(), restaurantId);
        checkNotFoundWithId(loadedMeal, meal.id());

        meal.setRestaurant(loadedMeal.getRestaurant());
        mealRepository.save(meal);
    }

    public List<MealTo> getAll() {
        return ToUtil.mealsAsToList(mealRepository.getAll());
    }

    public List<Meal> getAll(int restaurantId) {
        return mealRepository.getAllByRestaurantId(restaurantId);
    }

    public Meal get(int id, int restaurantId) throws NotFoundException {
        return checkNotFoundWithId(mealRepository.get(id, restaurantId), id);
    }

    public void delete(int id, int restaurantId) throws NotFoundException {
        checkNotFoundWithId(mealRepository.delete(id, restaurantId) != 0, id);
    }
}
