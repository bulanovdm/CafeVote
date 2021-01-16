package com.topjava.CafeVote.util;

import com.topjava.CafeVote.model.Meal;
import com.topjava.CafeVote.model.Menu;
import com.topjava.CafeVote.model.Restaurant;
import com.topjava.CafeVote.model.Vote;
import com.topjava.CafeVote.to.MealTo;
import com.topjava.CafeVote.to.MenuTo;
import com.topjava.CafeVote.to.RestaurantTo;
import com.topjava.CafeVote.to.VoteTo;
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
