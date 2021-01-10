package com.topjava.CafeVote;

import com.topjava.CafeVote.model.Meal;
import com.topjava.CafeVote.model.Menu;
import com.topjava.CafeVote.model.Restaurant;
import com.topjava.CafeVote.model.Vote;
import com.topjava.CafeVote.to.VoteTo;
import com.topjava.CafeVote.util.ToUtil;

import java.time.LocalDate;
import java.util.List;

import static com.topjava.CafeVote.UserTestData.*;

public class RestaurantTestData {

    public static final TestMatcher<Restaurant> RESTAURANT_TEST_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Restaurant.class);
    public static final TestMatcher<Meal> MEAL_TEST_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Meal.class);
    public static final TestMatcher<Menu> MENU_TEST_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Menu.class);

    public static final int START_RESTAURANTS_SEQ = 100;
    public static final int RES1_ID = START_RESTAURANTS_SEQ + 1;
    public static final int RES2_ID = START_RESTAURANTS_SEQ + 2;
    public static final int RES3_ID = START_RESTAURANTS_SEQ + 3;
    public static final int RES4_ID = START_RESTAURANTS_SEQ + 4;
    public static final int RES5_ID = START_RESTAURANTS_SEQ + 5;
    public static final int RES6_ID = START_RESTAURANTS_SEQ + 6;
    public static final int RES7_ID = START_RESTAURANTS_SEQ + 7;
    public static final int RES8_ID = START_RESTAURANTS_SEQ + 8;
    public static final int RES9_ID = START_RESTAURANTS_SEQ + 9;

    public static final Restaurant RES1 = new Restaurant(RES1_ID, "The Lady & Sons");
    public static final Restaurant RES2 = new Restaurant(RES2_ID, "Chez Panisse Cafe");
    public static final Restaurant RES3 = new Restaurant(RES3_ID, "Chipotle Mexican Grill");
    public static final Restaurant RES4 = new Restaurant(RES4_ID, "Wasabi by Morimoto");
    public static final Restaurant RES5 = new Restaurant(RES5_ID, "The Saffron Boutique");
    public static final Restaurant RES6 = new Restaurant(RES6_ID, "Pine & Dine");
    public static final Restaurant RES7 = new Restaurant(RES7_ID, "Vintage Machine");
    public static final Restaurant RES8 = new Restaurant(RES8_ID, "Banana Leaf");
    public static final Restaurant RES9 = new Restaurant(RES9_ID, "Yum Yum Tree");

    public static final List<Restaurant> RESTAURANTS = List.of(RES1, RES2, RES3, RES4, RES5, RES6, RES7, RES8, RES9);
    public static final List<Restaurant> RESTAURANTS_FOR_DAY_20190701 = List.of(RES9, RES1, RES3, RES4, RES5, RES7, RES2);
    public static final List<Restaurant> RESTAURANTS_FOR_DAY_20190701_WITHOUT_RES1 = List.of(RES9, RES3, RES4, RES5, RES7, RES2);

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

    public static final Meal Meal1 = new Meal(Meal1_ID, "Fries", RES1);
    public static final Meal Meal2 = new Meal(Meal2_ID, "Burger", RES1);
    public static final Meal Meal3 = new Meal(Meal3_ID, "Chicken Wings", RES1);
    public static final Meal Meal4 = new Meal(Meal4_ID, "Wok", RES2);
    public static final Meal Meal5 = new Meal(Meal5_ID, "Noodles", RES2);
    public static final Meal Meal6 = new Meal(Meal6_ID, "Salmon", RES2);
    public static final Meal Meal7 = new Meal(Meal7_ID, "Pie", RES3);
    public static final Meal Meal8 = new Meal(Meal8_ID, "Cake", RES3);
    public static final Meal Meal9 = new Meal(Meal9_ID, "Croissant", RES3);
    public static final Meal Meal10 = new Meal(Meal10_ID, "Pasta", RES4);
    public static final Meal Meal11 = new Meal(Meal11_ID, "Soup", RES4);
    public static final Meal Meal12 = new Meal(Meal12_ID, "Noodles", RES4);
    public static final Meal Meal13 = new Meal(Meal13_ID, "Beer", RES5);
    public static final Meal Meal14 = new Meal(Meal14_ID, "Ramen", RES5);
    public static final Meal Meal15 = new Meal(Meal15_ID, "Chicken with Rice", RES5);
    public static final Meal Meal16 = new Meal(Meal16_ID, "Fries", RES6);
    public static final Meal Meal17 = new Meal(Meal17_ID, "CheeseBurger", RES6);
    public static final Meal Meal18 = new Meal(Meal18_ID, "Pizza", RES6);
    public static final Meal Meal19 = new Meal(Meal19_ID, "Tomato Soup", RES7);
    public static final Meal Meal20 = new Meal(Meal20_ID, "CheeseBurger", RES7);
    public static final Meal Meal21 = new Meal(Meal21_ID, "Pizza", RES7);
    public static final Meal Meal22 = new Meal(Meal22_ID, "Tomato Soup", RES8);
    public static final Meal Meal23 = new Meal(Meal23_ID, "Tom Yum", RES8);
    public static final Meal Meal24 = new Meal(Meal24_ID, "Salad bar", RES8);
    public static final Meal Meal25 = new Meal(Meal25_ID, "Chicken Curry", RES9);
    public static final Meal Meal26 = new Meal(Meal26_ID, "Beer", RES9);
    public static final Meal Meal27 = new Meal(Meal27_ID, "Pizza", RES9);

    public static final List<Meal> RES3_MealES = List.of(Meal7, Meal8, Meal9);

    public static final List<Meal> MealES = List.of(Meal1, Meal2, Meal3, Meal4, Meal5, Meal6, Meal7,
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

    public static final Menu MENU1 = new Menu(MENU1_ID, LocalDate.of(2019, 7, 1), RES1, Meal1, 42);
    public static final Menu MENU2 = new Menu(MENU2_ID, LocalDate.of(2019, 7, 1), RES1, Meal2, 42);
    public static final Menu MENU3 = new Menu(MENU3_ID, LocalDate.of(2019, 7, 1), RES1, Meal3, 42);
    public static final Menu MENU4 = new Menu(MENU4_ID, LocalDate.of(2019, 7, 1), RES2, Meal4, 42);
    public static final Menu MENU5 = new Menu(MENU5_ID, LocalDate.of(2019, 7, 1), RES3, Meal7, 42);
    public static final Menu MENU6 = new Menu(MENU6_ID, LocalDate.of(2019, 7, 1), RES3, Meal8, 42);
    public static final Menu MENU7 = new Menu(MENU7_ID, LocalDate.of(2019, 7, 1), RES4, Meal12, 42);
    public static final Menu MENU8 = new Menu(MENU8_ID, LocalDate.of(2019, 7, 1), RES5, Meal14, 42);
    public static final Menu MENU9 = new Menu(MENU9_ID, LocalDate.of(2019, 7, 1), RES7, Meal19, 42);
    public static final Menu MENU10 = new Menu(MENU10_ID, LocalDate.of(2019, 7, 1), RES7, Meal20, 42);
    public static final Menu MENU11 = new Menu(MENU11_ID, LocalDate.of(2019, 7, 1), RES7, Meal21, 42);
    public static final Menu MENU12 = new Menu(MENU12_ID, LocalDate.of(2019, 7, 1), RES9, Meal25, 42);
    public static final Menu MENU13 = new Menu(MENU13_ID, LocalDate.of(2019, 7, 1), RES9, Meal26, 42);
    public static final Menu MENU14 = new Menu(MENU14_ID, LocalDate.of(2019, 7, 2), RES1, Meal1, 42);
    public static final Menu MENU15 = new Menu(MENU15_ID, LocalDate.of(2019, 7, 2), RES1, Meal2, 42);
    public static final Menu MENU16 = new Menu(MENU16_ID, LocalDate.of(2019, 7, 2), RES2, Meal5, 42);
    public static final Menu MENU17 = new Menu(MENU17_ID, LocalDate.of(2019, 7, 2), RES2, Meal4, 42);
    public static final Menu MENU18 = new Menu(MENU18_ID, LocalDate.of(2019, 7, 2), RES3, Meal9, 42);
    public static final Menu MENU19 = new Menu(MENU19_ID, LocalDate.of(2019, 7, 2), RES3, Meal8, 42);
    public static final Menu MENU20 = new Menu(MENU20_ID, LocalDate.of(2019, 7, 2), RES4, Meal12, 42);
    public static final Menu MENU21 = new Menu(MENU21_ID, LocalDate.of(2019, 7, 2), RES5, Meal14, 42);
    public static final Menu MENU22 = new Menu(MENU22_ID, LocalDate.of(2019, 7, 2), RES5, Meal15, 42);
    public static final Menu MENU23 = new Menu(MENU23_ID, LocalDate.of(2019, 7, 2), RES7, Meal20, 42);
    public static final Menu MENU24 = new Menu(MENU24_ID, LocalDate.of(2019, 7, 2), RES8, Meal23, 42);
    public static final Menu MENU25 = new Menu(MENU25_ID, LocalDate.of(2019, 7, 2), RES9, Meal25, 42);
    public static final Menu MENU26 = new Menu(MENU26_ID, LocalDate.of(2019, 7, 2), RES8, Meal22, 42);
    public static final Menu MENU27 = new Menu(MENU27_ID, LocalDate.of(2019, 7, 3), RES1, Meal1, 42);
    public static final Menu MENU28 = new Menu(MENU28_ID, LocalDate.of(2019, 7, 3), RES2, Meal6, 42);
    public static final Menu MENU29 = new Menu(MENU29_ID, LocalDate.of(2019, 7, 3), RES1, Meal3, 42);
    public static final Menu MENU30 = new Menu(MENU30_ID, LocalDate.of(2019, 7, 3), RES2, Meal4, 42);
    public static final Menu MENU31 = new Menu(MENU31_ID, LocalDate.of(2019, 7, 3), RES5, Meal13, 42);
    public static final Menu MENU32 = new Menu(MENU32_ID, LocalDate.of(2019, 7, 3), RES3, Meal8, 42);
    public static final Menu MENU33 = new Menu(MENU33_ID, LocalDate.of(2019, 7, 3), RES4, Meal12, 42);
    public static final Menu MENU34 = new Menu(MENU34_ID, LocalDate.of(2019, 7, 3), RES8, Meal22, 42);
    public static final Menu MENU35 = new Menu(MENU35_ID, LocalDate.of(2019, 7, 3), RES8, Meal23, 42);
    public static final Menu MENU36 = new Menu(MENU36_ID, LocalDate.of(2019, 7, 3), RES8, Meal24, 42);
    public static final Menu MENU37 = new Menu(MENU37_ID, LocalDate.of(2019, 7, 3), RES7, Meal21, 42);
    public static final Menu MENU38 = new Menu(MENU38_ID, LocalDate.of(2019, 7, 3), RES9, Meal27, 42);
    public static final Menu MENU39 = new Menu(MENU39_ID, LocalDate.of(2019, 7, 3), RES9, Meal26, 42);

    public static final List<Menu> RES8_MENUS = List.of(MENU24, MENU26, MENU34, MENU35, MENU36);

    public static final List<Menu> MENUS_FOR_20190703 = List.of(MENU27, MENU28, MENU29, MENU30,
            MENU31, MENU32, MENU33, MENU34, MENU35, MENU36, MENU37, MENU38, MENU39);

    public static final List<Menu> MENUS_FOR_20190702 = List.of(MENU14, MENU15, MENU16, MENU17,
            MENU18, MENU19, MENU20, MENU21, MENU22, MENU23, MENU24, MENU25, MENU26);

    public static final List<Menu> DAY_MENUS = List.of(MENU1, MENU2, MENU3, MENU4, MENU5, MENU6,
            MENU7, MENU8, MENU9, MENU10, MENU11, MENU12, MENU13, MENU14, MENU15, MENU16,
            MENU17, MENU18, MENU19, MENU20, MENU21, MENU22, MENU23, MENU24, MENU25, MENU26,
            MENU27, MENU28, MENU29, MENU30, MENU31, MENU32, MENU33, MENU34, MENU35, MENU36,
            MENU37, MENU38, MENU39);

    public static final int START_VOTE_SEQ = 50000;
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

    public static final Vote VOTE1 = new Vote(VOTE1_ID, LocalDate.of(2020, 1, 10), USER8, RES9);
    public static final Vote VOTE2 = new Vote(VOTE2_ID, LocalDate.of(2020, 1, 10), USER1, RES1);
    public static final Vote VOTE3 = new Vote(VOTE3_ID, LocalDate.of(2020, 1, 10), USER2, RES3);
    public static final Vote VOTE4 = new Vote(VOTE4_ID, LocalDate.of(2020, 1, 10), USER3, RES5);
    public static final Vote VOTE5 = new Vote(VOTE5_ID, LocalDate.of(2020, 1, 10), USER4, RES3);
    public static final Vote VOTE6 = new Vote(VOTE6_ID, LocalDate.of(2020, 1, 10), USER5, RES3);
    public static final Vote VOTE7 = new Vote(VOTE7_ID, LocalDate.of(2020, 1, 10), USER6, RES1);
    public static final Vote VOTE8 = new Vote(VOTE8_ID, LocalDate.of(2020, 1, 10), USER7, RES8);
    public static final Vote VOTE9 = new Vote(VOTE9_ID, LocalDate.of(2020, 1, 10), USER8, RES9);
    public static final Vote VOTE10 = new Vote(VOTE10_ID, LocalDate.of(2020, 1, 11), USER1, RES7);
    public static final Vote VOTE11 = new Vote(VOTE11_ID, LocalDate.of(2020, 1, 11), USER2, RES3);
    public static final Vote VOTE12 = new Vote(VOTE12_ID, LocalDate.of(2020, 1, 11), USER3, RES6);
    public static final Vote VOTE13 = new Vote(VOTE13_ID, LocalDate.of(2020, 1, 11), USER4, RES3);
    public static final Vote VOTE14 = new Vote(VOTE14_ID, LocalDate.of(2020, 1, 11), USER5, RES5);
    public static final Vote VOTE15 = new Vote(VOTE15_ID, LocalDate.of(2020, 1, 11), USER6, RES1);
    public static final Vote VOTE16 = new Vote(VOTE16_ID, LocalDate.of(2020, 1, 11), USER7, RES2);
    public static final Vote VOTE17 = new Vote(VOTE17_ID, LocalDate.of(2020, 1, 11), USER8, RES3);
    public static final Vote VOTE18 = new Vote(VOTE18_ID, LocalDate.of(2020, 1, 11), USER1, RES3);
    public static final Vote VOTE19 = new Vote(VOTE19_ID, LocalDate.of(2020, 1, 11), USER2, RES3);
    public static final Vote VOTE20 = new Vote(VOTE20_ID, LocalDate.of(2020, 1, 11), USER3, RES8);
    public static final Vote VOTE21 = new Vote(VOTE21_ID, LocalDate.of(2020, 1, 11), USER4, RES8);
    public static final Vote VOTE22 = new Vote(VOTE22_ID, LocalDate.of(2020, 1, 11), USER5, RES8);
    public static final Vote VOTE23 = new Vote(VOTE23_ID, LocalDate.of(2020, 1, 11), USER6, RES1);
    public static final Vote VOTE24 = new Vote(VOTE24_ID, LocalDate.of(2020, 1, 11), USER7, RES4);

    public static final List<VoteTo> VOTE_TOS_FOR_20190713 = ToUtil.votesAsToList(List.of(VOTE17, VOTE18, VOTE19, VOTE20, VOTE21, VOTE22, VOTE23, VOTE24));
    public static final List<VoteTo> VOTE_TOS_FOR_20190713_FOR_USER3 = ToUtil.votesAsToList(List.of(VOTE20));
    public static final List<VoteTo> VOTE_TOS_FOR_20190713_FOR_RES8 = ToUtil.votesAsToList(List.of(VOTE20, VOTE21, VOTE22));
}
