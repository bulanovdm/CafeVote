package com.topjava.CafeVote.service;

import com.topjava.CafeVote.error.NotFoundException;
import com.topjava.CafeVote.model.Restaurant;
import com.topjava.CafeVote.repository.RestaurantRepository;
import com.topjava.CafeVote.to.RestaurantTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.topjava.CafeVote.util.ToUtil.restaurantsAsToList;
import static com.topjava.CafeVote.util.ValidationUtil.checkNew;
import static com.topjava.CafeVote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNew(restaurant);
        return restaurantRepository.save(restaurant);
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurant, restaurant.id());
        restaurantRepository.save(restaurant);
    }

    public List<RestaurantTo> getAll() {
        return restaurantsAsToList(restaurantRepository.getAll());
    }

    public Restaurant get(int id) throws NotFoundException {
        Restaurant restaurant = restaurantRepository.getFullById(id);
        if (restaurant == null) {
            return restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("Restaurant not found"));
        }
        return checkNotFoundWithId(restaurant, id);
    }

    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(restaurantRepository.delete(id) != 0, id);
    }

    public Restaurant getForDay(int id, LocalDate localDate) throws NotFoundException {
        return checkNotFoundWithId(restaurantRepository.getByIdForDay(id, localDate), id);
    }

    public List<Restaurant> getAllForDay(LocalDate localDate) {
        return restaurantRepository.getAllForDay(localDate);
    }

    public List<Restaurant> getAllForToday() {
        return restaurantRepository.getAllForDay(LocalDate.now());
    }
}
