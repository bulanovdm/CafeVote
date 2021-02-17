package com.topjava.cafevote.web.restaurant;

import com.topjava.cafevote.service.MealService;
import com.topjava.cafevote.service.MenuService;
import com.topjava.cafevote.service.RestaurantService;
import com.topjava.cafevote.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AbstractRestaurantRestController {
    public static final String ADMIN_RESTAURANTS_REST_URL = "/api/admin/restaurants";
    public static final String USER_RESTAURANTS_REST_URL = "/api/restaurants";

    protected final RestaurantService restaurantService;
    protected final MealService mealService;
    protected final MenuService menuService;
    protected final VoteService voteService;

    @Autowired
    public AbstractRestaurantRestController(RestaurantService restaurantService, MealService mealService,
                                            MenuService menuService, VoteService voteService) {
        this.restaurantService = restaurantService;
        this.mealService = mealService;
        this.menuService = menuService;
        this.voteService = voteService;
    }
}

