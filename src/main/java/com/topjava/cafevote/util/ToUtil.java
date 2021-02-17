package com.topjava.cafevote.util;

import com.topjava.cafevote.model.Meal;
import com.topjava.cafevote.model.Menu;
import com.topjava.cafevote.model.Restaurant;
import com.topjava.cafevote.model.Vote;
import com.topjava.cafevote.to.MealTo;
import com.topjava.cafevote.to.MenuTo;
import com.topjava.cafevote.to.RestaurantTo;
import com.topjava.cafevote.to.VoteTo;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ToUtil {

    public static List<RestaurantTo> restaurantsAsToList(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantTo::new)
                .collect(Collectors.toList());
    }

    public static List<MealTo> mealsAsToList(List<Meal> dishes) {
        return dishes.stream()
                .map(MealTo::new)
                .collect(Collectors.toList());
    }

    public static List<MenuTo> menusAsToList(List<Menu> dayMenus) {
        return dayMenus.stream()
                .map(MenuTo::new)
                .collect(Collectors.toList());
    }

    public static List<VoteTo> votesAsToList(List<Vote> votes) {
        return votes.stream()
                .map(VoteTo::new)
                .collect(Collectors.toList());
    }
}
