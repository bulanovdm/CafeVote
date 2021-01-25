package com.topjava.CafeVote.service;

import com.topjava.CafeVote.error.NotFoundException;
import com.topjava.CafeVote.model.Menu;
import com.topjava.CafeVote.repository.MenuRepository;
import com.topjava.CafeVote.repository.RestaurantRepository;
import com.topjava.CafeVote.to.MenuTo;
import com.topjava.CafeVote.util.ToUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.topjava.CafeVote.util.ValidationUtil.checkNew;
import static com.topjava.CafeVote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final MealService mealService;

    @Autowired
    public MenuService(MenuRepository menuRepository, RestaurantRepository restaurantRepository, MealService mealService) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.mealService = mealService;
    }

    @Transactional
    public Menu create(Menu menu, int restaurantId, int mealId) {
        Assert.notNull(menu, "Menu must not be null");
        checkNew(menu);
        if (menu.getMenuDate() == null) menu.setMenuDate(LocalDate.now());
        menu.setRestaurant(restaurantRepository.getOne(restaurantId));
        menu.setMeal(mealService.get(mealId, restaurantId));
        return menuRepository.save(menu);
    }

    @Transactional
    public void update(Menu menu, int restaurantId, int mealId) {
        Assert.notNull(menu, "Menu must not be null");
        checkNotFoundWithId(menuRepository.get(menu.id(), restaurantId), menu.id());
        menu.setRestaurant(restaurantRepository.getOne(restaurantId));
        menu.setMeal(mealService.get(mealId, restaurantId));
        menuRepository.save(menu);
    }

    public List<MenuTo> getAll() {
        return ToUtil.menusAsToList(menuRepository.findAll());
    }

    public List<Menu> getAllForRestaurantId(int restaurantId) {
        return menuRepository.getAllByRestaurantId(restaurantId);
    }

    public List<Menu> getAllForByDateRestaurantId(int restaurantId, LocalDate date) {
        return menuRepository.getAllForDateAndRestaurantId(restaurantId, date);
    }

    public List<Menu> getAllForDate(LocalDate date) {
        return menuRepository.getAllForDate(date);
    }

    public Menu get(int id, int restaurantId) throws NotFoundException {
        return checkNotFoundWithId(menuRepository.get(id, restaurantId), id);
    }

    public void delete(int id, int restaurantId) throws NotFoundException {
        checkNotFoundWithId(menuRepository.delete(id, restaurantId) != 0, id);
    }

    public void deleteAllForDate(int restaurantId, LocalDate date) {
        checkNotFoundWithId(menuRepository.deleteAll(restaurantId, date) != 0, restaurantId);
    }
}
