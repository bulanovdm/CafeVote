package com.topjava.cafevote.service;

import com.topjava.cafevote.error.NotFoundException;
import com.topjava.cafevote.model.Menu;
import com.topjava.cafevote.repository.MenuRepository;
import com.topjava.cafevote.repository.RestaurantRepository;
import com.topjava.cafevote.to.MenuTo;
import com.topjava.cafevote.util.ToUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.topjava.cafevote.util.DateTimeUtil.getCurrentDate;
import static com.topjava.cafevote.util.ValidationUtil.checkNotFoundWithId;

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
        if (menu.getMenuDate() == null) menu.setMenuDate(getCurrentDate());
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
