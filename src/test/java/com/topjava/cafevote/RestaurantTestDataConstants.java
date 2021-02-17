package com.topjava.cafevote;

import com.topjava.cafevote.model.Meal;
import com.topjava.cafevote.model.Menu;
import com.topjava.cafevote.model.Restaurant;
import com.topjava.cafevote.model.Vote;
import com.topjava.cafevote.to.MealTo;
import com.topjava.cafevote.to.MenuTo;
import com.topjava.cafevote.to.VoteTo;
import com.topjava.cafevote.util.ToUtil;

import java.util.List;

import static com.topjava.cafevote.UserTestDataConstants.*;
import static java.time.LocalDate.of;

public class RestaurantTestDataConstants {

    public static final TestMatcher<Restaurant> RESTAURANT_TEST_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Restaurant.class);
    public static final TestMatcher<Meal> MEAL_TEST_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Meal.class, "restaurant");
    public static final TestMatcher<MealTo> MEALTO_TEST_MATCHER = TestMatcher.usingIgnoringFieldsComparator(MealTo.class);
    public static final TestMatcher<Menu> MENU_TEST_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Menu.class, "restaurant", "meal");
    public static final TestMatcher<MenuTo> MENUTO_TEST_MATCHER = TestMatcher.usingIgnoringFieldsComparator(MenuTo.class);
    public static final TestMatcher<Vote> VOTE_TEST_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Vote.class);
    public static final TestMatcher<VoteTo> VOTETO_TEST_MATCHER = TestMatcher.usingIgnoringFieldsComparator(VoteTo.class);

    public static final int START_RESTAURANTS_SEQ = 100;
    public static final int RESTAURANT_1ID = START_RESTAURANTS_SEQ + 1;
    public static final int RESTAURANT_2ID = START_RESTAURANTS_SEQ + 2;
    public static final int RESTAURANT_3ID = START_RESTAURANTS_SEQ + 3;
    public static final int RESTAURANT_4ID = START_RESTAURANTS_SEQ + 4;
    public static final int RESTAURANT_5ID = START_RESTAURANTS_SEQ + 5;
    public static final int RESTAURANT_6ID = START_RESTAURANTS_SEQ + 6;
    public static final int RESTAURANT_7ID = START_RESTAURANTS_SEQ + 7;
    public static final int RESTAURANT_8ID = START_RESTAURANTS_SEQ + 8;
    public static final int RESTAURANT_9ID = START_RESTAURANTS_SEQ + 9;
    public static final int RESTAURANT_10ID_NEW = START_RESTAURANTS_SEQ + 10;

    public static final int RESTAURANT_ID_NOT_FOUND = START_RESTAURANTS_SEQ + 42;

    public static final Restaurant REST_1 = new Restaurant(RESTAURANT_1ID, "The Lady & Sons");
    public static final Restaurant REST_2 = new Restaurant(RESTAURANT_2ID, "Chez Panisse Cafe");
    public static final Restaurant REST_3 = new Restaurant(RESTAURANT_3ID, "Chipotle Mexican Grill");
    public static final Restaurant REST_4 = new Restaurant(RESTAURANT_4ID, "Wasabi by Morimoto");
    public static final Restaurant REST_5 = new Restaurant(RESTAURANT_5ID, "The Saffron Boutique");
    public static final Restaurant REST_6 = new Restaurant(RESTAURANT_6ID, "Pine & Dine");
    public static final Restaurant REST_7 = new Restaurant(RESTAURANT_7ID, "Vintage Machine");
    public static final Restaurant REST_8 = new Restaurant(RESTAURANT_8ID, "Banana Leaf");
    public static final Restaurant REST_9 = new Restaurant(RESTAURANT_9ID, "Yum Yum Tree");

    public static final List<Restaurant> RESTAURANT_LIST = List.of(REST_1, REST_2, REST_3, REST_4, REST_5, REST_6, REST_7, REST_8, REST_9);

    public static final int START_MealES_SEQ = 1000;
    public static final int Meal1_ID = START_MealES_SEQ + 1;
    public static final int Meal2_ID = START_MealES_SEQ + 2;
    public static final int Meal3_ID = START_MealES_SEQ + 3;
    public static final int Meal4_ID = START_MealES_SEQ + 4;
    public static final int Meal5_ID = START_MealES_SEQ + 5;
    public static final int Meal6_ID = START_MealES_SEQ + 6;
    public static final int Meal7_ID = START_MealES_SEQ + 7;
    public static final int Meal8_ID = START_MealES_SEQ + 8;
    public static final int Meal9_ID = START_MealES_SEQ + 9;
    public static final int Meal10_ID = START_MealES_SEQ + 10;
    public static final int Meal11_ID = START_MealES_SEQ + 11;
    public static final int Meal12_ID = START_MealES_SEQ + 12;
    public static final int Meal13_ID = START_MealES_SEQ + 13;
    public static final int Meal14_ID = START_MealES_SEQ + 14;
    public static final int Meal15_ID = START_MealES_SEQ + 15;
    public static final int Meal16_ID = START_MealES_SEQ + 16;
    public static final int Meal17_ID = START_MealES_SEQ + 17;
    public static final int Meal18_ID = START_MealES_SEQ + 18;
    public static final int Meal19_ID = START_MealES_SEQ + 19;
    public static final int Meal20_ID = START_MealES_SEQ + 20;
    public static final int Meal21_ID = START_MealES_SEQ + 21;
    public static final int Meal22_ID = START_MealES_SEQ + 22;
    public static final int Meal23_ID = START_MealES_SEQ + 23;
    public static final int Meal24_ID = START_MealES_SEQ + 24;
    public static final int Meal25_ID = START_MealES_SEQ + 25;
    public static final int Meal26_ID = START_MealES_SEQ + 26;
    public static final int Meal27_ID = START_MealES_SEQ + 27;

    public static final Meal Meal1 = new Meal(Meal1_ID, "Fries", REST_1);
    public static final Meal Meal2 = new Meal(Meal2_ID, "Burger", REST_1);
    public static final Meal Meal3 = new Meal(Meal3_ID, "Chicken Wings", REST_1);
    public static final Meal Meal4 = new Meal(Meal4_ID, "Wok", REST_2);
    public static final Meal Meal5 = new Meal(Meal5_ID, "Noodles", REST_2);
    public static final Meal Meal6 = new Meal(Meal6_ID, "Salmon", REST_2);
    public static final Meal Meal7 = new Meal(Meal7_ID, "Pie", REST_3);
    public static final Meal Meal8 = new Meal(Meal8_ID, "Cake", REST_3);
    public static final Meal Meal9 = new Meal(Meal9_ID, "Croissant", REST_3);
    public static final Meal Meal10 = new Meal(Meal10_ID, "Pasta", REST_4);
    public static final Meal Meal11 = new Meal(Meal11_ID, "Soup", REST_4);
    public static final Meal Meal12 = new Meal(Meal12_ID, "Noodles", REST_4);
    public static final Meal Meal13 = new Meal(Meal13_ID, "Beer", REST_5);
    public static final Meal Meal14 = new Meal(Meal14_ID, "Ramen", REST_5);
    public static final Meal Meal15 = new Meal(Meal15_ID, "Chicken with Rice", REST_5);
    public static final Meal Meal16 = new Meal(Meal16_ID, "Fries", REST_6);
    public static final Meal Meal17 = new Meal(Meal17_ID, "Salmon", REST_6);
    public static final Meal Meal18 = new Meal(Meal18_ID, "Chicken Wings", REST_6);
    public static final Meal Meal19 = new Meal(Meal19_ID, "Fries", REST_7);
    public static final Meal Meal20 = new Meal(Meal20_ID, "CheeseBurger", REST_7);
    public static final Meal Meal21 = new Meal(Meal21_ID, "Pizza", REST_7);
    public static final Meal Meal22 = new Meal(Meal22_ID, "Tomato Soup", REST_8);
    public static final Meal Meal23 = new Meal(Meal23_ID, "Tom Yum", REST_8);
    public static final Meal Meal24 = new Meal(Meal24_ID, "Salad bar", REST_8);
    public static final Meal Meal25 = new Meal(Meal25_ID, "Chicken Curry", REST_9);
    public static final Meal Meal26 = new Meal(Meal26_ID, "Beer", REST_9);
    public static final Meal Meal27 = new Meal(Meal27_ID, "Pizza", REST_9);

    public static final List<Meal> RESTAURANT3_MEAL_LIST = List.of(Meal7, Meal8, Meal9);

    public static final List<Meal> MEAL_LIST = List.of(Meal1, Meal2, Meal3, Meal4, Meal5, Meal6, Meal7,
            Meal8, Meal9, Meal10, Meal11, Meal12, Meal13, Meal14, Meal15, Meal16, Meal17, Meal18,
            Meal19, Meal20, Meal21, Meal22, Meal23, Meal24, Meal25, Meal26, Meal27);

    public static final int START_MENU_SEQ = 10000;
    public static final int MENU1_ID = START_MENU_SEQ + 1;
    public static final int MENU2_ID = START_MENU_SEQ + 2;
    public static final int MENU3_ID = START_MENU_SEQ + 3;
    public static final int MENU4_ID = START_MENU_SEQ + 4;
    public static final int MENU5_ID = START_MENU_SEQ + 5;
    public static final int MENU6_ID = START_MENU_SEQ + 6;
    public static final int MENU7_ID = START_MENU_SEQ + 7;
    public static final int MENU8_ID = START_MENU_SEQ + 8;
    public static final int MENU9_ID = START_MENU_SEQ + 9;
    public static final int MENU10_ID = START_MENU_SEQ + 10;
    public static final int MENU11_ID = START_MENU_SEQ + 11;
    public static final int MENU12_ID = START_MENU_SEQ + 12;
    public static final int MENU13_ID = START_MENU_SEQ + 13;
    public static final int MENU14_ID = START_MENU_SEQ + 14;
    public static final int MENU15_ID = START_MENU_SEQ + 15;
    public static final int MENU16_ID = START_MENU_SEQ + 16;
    public static final int MENU17_ID = START_MENU_SEQ + 17;
    public static final int MENU18_ID = START_MENU_SEQ + 18;
    public static final int MENU19_ID = START_MENU_SEQ + 19;
    public static final int MENU20_ID = START_MENU_SEQ + 20;
    public static final int MENU21_ID = START_MENU_SEQ + 21;
    public static final int MENU22_ID = START_MENU_SEQ + 22;
    public static final int MENU23_ID = START_MENU_SEQ + 23;
    public static final int MENU24_ID = START_MENU_SEQ + 24;
    public static final int MENU25_ID = START_MENU_SEQ + 25;
    public static final int MENU26_ID = START_MENU_SEQ + 26;
    public static final int MENU27_ID = START_MENU_SEQ + 27;
    public static final int MENU28_ID = START_MENU_SEQ + 28;
    public static final int MENU29_ID = START_MENU_SEQ + 29;
    public static final int MENU30_ID = START_MENU_SEQ + 30;
    public static final int MENU31_ID = START_MENU_SEQ + 31;
    public static final int MENU32_ID = START_MENU_SEQ + 32;
    public static final int MENU33_ID = START_MENU_SEQ + 33;
    public static final int MENU34_ID = START_MENU_SEQ + 34;
    public static final int MENU35_ID = START_MENU_SEQ + 35;
    public static final int MENU36_ID = START_MENU_SEQ + 36;
    public static final int MENU37_ID = START_MENU_SEQ + 37;
    public static final int MENU38_ID = START_MENU_SEQ + 38;
    public static final int MENU39_ID = START_MENU_SEQ + 39;

    public static final Menu MENU1 = new Menu(MENU1_ID, of(2021, 1, 10), REST_1, Meal1, 42);
    public static final Menu MENU2 = new Menu(MENU2_ID, of(2021, 1, 10), REST_1, Meal2, 42);
    public static final Menu MENU3 = new Menu(MENU3_ID, of(2021, 1, 10), REST_1, Meal3, 42);
    public static final Menu MENU4 = new Menu(MENU4_ID, of(2021, 1, 10), REST_2, Meal4, 42);
    public static final Menu MENU5 = new Menu(MENU5_ID, of(2021, 1, 10), REST_3, Meal7, 42);
    public static final Menu MENU6 = new Menu(MENU6_ID, of(2021, 1, 10), REST_3, Meal8, 42);
    public static final Menu MENU7 = new Menu(MENU7_ID, of(2021, 1, 10), REST_4, Meal12, 42);
    public static final Menu MENU8 = new Menu(MENU8_ID, of(2021, 1, 10), REST_5, Meal14, 42);
    public static final Menu MENU9 = new Menu(MENU9_ID, of(2021, 1, 10), REST_7, Meal19, 42);
    public static final Menu MENU10 = new Menu(MENU10_ID, of(2021, 1, 10), REST_7, Meal20, 42);
    public static final Menu MENU11 = new Menu(MENU11_ID, of(2021, 1, 10), REST_7, Meal21, 42);
    public static final Menu MENU12 = new Menu(MENU12_ID, of(2021, 1, 10), REST_9, Meal25, 42);
    public static final Menu MENU13 = new Menu(MENU13_ID, of(2021, 1, 10), REST_9, Meal26, 42);
    public static final Menu MENU14 = new Menu(MENU14_ID, of(2021, 1, 11), REST_1, Meal1, 42);
    public static final Menu MENU15 = new Menu(MENU15_ID, of(2021, 1, 11), REST_1, Meal2, 42);
    public static final Menu MENU16 = new Menu(MENU16_ID, of(2021, 1, 11), REST_2, Meal5, 42);
    public static final Menu MENU17 = new Menu(MENU17_ID, of(2021, 1, 11), REST_2, Meal4, 42);
    public static final Menu MENU18 = new Menu(MENU18_ID, of(2021, 1, 11), REST_3, Meal9, 42);
    public static final Menu MENU19 = new Menu(MENU19_ID, of(2021, 1, 11), REST_3, Meal8, 42);
    public static final Menu MENU20 = new Menu(MENU20_ID, of(2021, 1, 11), REST_4, Meal12, 42);
    public static final Menu MENU21 = new Menu(MENU21_ID, of(2021, 1, 11), REST_5, Meal14, 42);
    public static final Menu MENU22 = new Menu(MENU22_ID, of(2021, 1, 11), REST_5, Meal15, 42);
    public static final Menu MENU23 = new Menu(MENU23_ID, of(2021, 1, 11), REST_7, Meal20, 42);
    public static final Menu MENU24 = new Menu(MENU24_ID, of(2021, 1, 11), REST_8, Meal23, 42);
    public static final Menu MENU25 = new Menu(MENU25_ID, of(2021, 1, 11), REST_9, Meal25, 42);
    public static final Menu MENU26 = new Menu(MENU26_ID, of(2021, 1, 11), REST_8, Meal22, 42);
    public static final Menu MENU27 = new Menu(MENU27_ID, of(2021, 1, 12), REST_1, Meal1, 42);
    public static final Menu MENU28 = new Menu(MENU28_ID, of(2021, 1, 12), REST_2, Meal6, 42);
    public static final Menu MENU29 = new Menu(MENU29_ID, of(2021, 1, 12), REST_1, Meal3, 42);
    public static final Menu MENU30 = new Menu(MENU30_ID, of(2021, 1, 12), REST_2, Meal4, 42);
    public static final Menu MENU31 = new Menu(MENU31_ID, of(2021, 1, 12), REST_5, Meal13, 42);
    public static final Menu MENU32 = new Menu(MENU32_ID, of(2021, 1, 12), REST_3, Meal8, 42);
    public static final Menu MENU33 = new Menu(MENU33_ID, of(2021, 1, 12), REST_4, Meal12, 42);
    public static final Menu MENU34 = new Menu(MENU34_ID, of(2021, 1, 12), REST_8, Meal22, 42);
    public static final Menu MENU35 = new Menu(MENU35_ID, of(2021, 1, 12), REST_8, Meal23, 42);
    public static final Menu MENU36 = new Menu(MENU36_ID, of(2021, 1, 12), REST_8, Meal24, 42);
    public static final Menu MENU37 = new Menu(MENU37_ID, of(2021, 1, 12), REST_7, Meal21, 42);
    public static final Menu MENU38 = new Menu(MENU38_ID, of(2021, 1, 12), REST_9, Meal27, 42);
    public static final Menu MENU39 = new Menu(MENU39_ID, of(2021, 1, 12), REST_9, Meal26, 42);

    public static final List<Menu> RESTAURANT_8ID_MENUS = List.of(MENU24, MENU26, MENU34, MENU35, MENU36);

    public static final List<Menu> MENUS_FOR_20210110 = List.of(MENU27, MENU28, MENU29, MENU30,
            MENU31, MENU32, MENU33, MENU34, MENU35, MENU36, MENU37, MENU38, MENU39);

    public static final List<Menu> MENUS_FOR_20210111 = List.of(MENU14, MENU15, MENU16, MENU17,
            MENU18, MENU19, MENU20, MENU21, MENU22, MENU23, MENU24, MENU25, MENU26);

    public static final List<Menu> DAY_MENUS = List.of(MENU1, MENU2, MENU3, MENU4, MENU5, MENU6,
            MENU7, MENU8, MENU9, MENU10, MENU11, MENU12, MENU13, MENU14, MENU15, MENU16,
            MENU17, MENU18, MENU19, MENU20, MENU21, MENU22, MENU23, MENU24, MENU25, MENU26,
            MENU27, MENU28, MENU29, MENU30, MENU31, MENU32, MENU33, MENU34, MENU35, MENU36,
            MENU37, MENU38, MENU39);

    public static final int START_VOTE_SEQ = 20000;
    public static final int VOTE1_ID = START_VOTE_SEQ + 1;
    public static final int VOTE2_ID = START_VOTE_SEQ + 2;
    public static final int VOTE3_ID = START_VOTE_SEQ + 3;
    public static final int VOTE4_ID = START_VOTE_SEQ + 4;
    public static final int VOTE5_ID = START_VOTE_SEQ + 5;
    public static final int VOTE6_ID = START_VOTE_SEQ + 6;
    public static final int VOTE7_ID = START_VOTE_SEQ + 7;
    public static final int VOTE8_ID = START_VOTE_SEQ + 8;
    public static final int VOTE9_ID = START_VOTE_SEQ + 9;
    public static final int VOTE10_ID = START_VOTE_SEQ + 10;
    public static final int VOTE11_ID = START_VOTE_SEQ + 11;
    public static final int VOTE12_ID = START_VOTE_SEQ + 12;
    public static final int VOTE13_ID = START_VOTE_SEQ + 13;
    public static final int VOTE14_ID = START_VOTE_SEQ + 14;
    public static final int VOTE15_ID = START_VOTE_SEQ + 15;
    public static final int VOTE16_ID = START_VOTE_SEQ + 16;
    public static final int VOTE17_ID = START_VOTE_SEQ + 17;
    public static final int VOTE18_ID = START_VOTE_SEQ + 18;
    public static final int VOTE19_ID = START_VOTE_SEQ + 19;
    public static final int VOTE20_ID = START_VOTE_SEQ + 20;
    public static final int VOTE21_ID = START_VOTE_SEQ + 21;
    public static final int VOTE22_ID = START_VOTE_SEQ + 22;
    public static final int VOTE23_ID = START_VOTE_SEQ + 23;
    public static final int VOTE24_ID = START_VOTE_SEQ + 24;

    public static final Vote VOTE1 = new Vote(VOTE1_ID, of(2021, 1, 10), USER7, REST_9);
    public static final Vote VOTE2 = new Vote(VOTE2_ID, of(2021, 1, 10), ADMIN, REST_1);
    public static final Vote VOTE3 = new Vote(VOTE3_ID, of(2021, 1, 10), USER1, REST_3);
    public static final Vote VOTE4 = new Vote(VOTE4_ID, of(2021, 1, 10), USER2, REST_5);
    public static final Vote VOTE5 = new Vote(VOTE5_ID, of(2021, 1, 10), USER3, REST_3);
    public static final Vote VOTE6 = new Vote(VOTE6_ID, of(2021, 1, 10), USER4, REST_3);
    public static final Vote VOTE7 = new Vote(VOTE7_ID, of(2021, 1, 10), USER5, REST_1);
    public static final Vote VOTE8 = new Vote(VOTE8_ID, of(2021, 1, 10), USER6, REST_8);
    public static final Vote VOTE9 = new Vote(VOTE9_ID, of(2021, 1, 10), USER7, REST_9);
    public static final Vote VOTE10 = new Vote(VOTE10_ID, of(2021, 1, 11), ADMIN, REST_7);
    public static final Vote VOTE11 = new Vote(VOTE11_ID, of(2021, 1, 11), USER1, REST_3);
    public static final Vote VOTE12 = new Vote(VOTE12_ID, of(2021, 1, 11), USER2, REST_6);
    public static final Vote VOTE13 = new Vote(VOTE13_ID, of(2021, 1, 11), USER3, REST_3);
    public static final Vote VOTE14 = new Vote(VOTE14_ID, of(2021, 1, 11), USER4, REST_5);
    public static final Vote VOTE15 = new Vote(VOTE15_ID, of(2021, 1, 11), USER5, REST_1);
    public static final Vote VOTE16 = new Vote(VOTE16_ID, of(2021, 1, 11), USER6, REST_2);
    public static final Vote VOTE17 = new Vote(VOTE17_ID, of(2021, 1, 12), USER7, REST_3);
    public static final Vote VOTE18 = new Vote(VOTE18_ID, of(2021, 1, 12), ADMIN, REST_3);
    public static final Vote VOTE19 = new Vote(VOTE19_ID, of(2021, 1, 12), USER1, REST_3);
    public static final Vote VOTE20 = new Vote(VOTE20_ID, of(2021, 1, 12), USER2, REST_8);
    public static final Vote VOTE21 = new Vote(VOTE21_ID, of(2021, 1, 12), USER3, REST_8);
    public static final Vote VOTE22 = new Vote(VOTE22_ID, of(2021, 1, 12), USER4, REST_8);
    public static final Vote VOTE23 = new Vote(VOTE23_ID, of(2021, 1, 12), USER5, REST_1);
    public static final Vote VOTE24 = new Vote(VOTE24_ID, of(2021, 1, 12), USER6, REST_4);

    public static final List<VoteTo> VOTE_TOS_FOR_20210713 = ToUtil.votesAsToList(List.of(VOTE17, VOTE18, VOTE19, VOTE20, VOTE21, VOTE22, VOTE23, VOTE24));
    public static final List<VoteTo> VOTE_TOS_FOR_20210713_FOR_USER3 = ToUtil.votesAsToList(List.of(VOTE20));
    public static final List<VoteTo> VOTE_TOS_FOR_20210111_FOR_RES8 = ToUtil.votesAsToList(List.of(VOTE20, VOTE21, VOTE22));
}
