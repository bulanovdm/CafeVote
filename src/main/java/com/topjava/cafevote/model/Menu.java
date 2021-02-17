package com.topjava.cafevote.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.topjava.cafevote.util.DateTimeUtil;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.topjava.cafevote.util.DateTimeUtil.getCurrentDate;

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(
        columnNames = {"menu_date", "restaurant_id", "meal_id"},
        name = "menus_unique_menuday_restaurantid_mealid_idx")})
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Menu extends AbstractBaseEntity {

    @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
    @JsonFormat(pattern = DateTimeUtil.DATE_PATTERN)
    @Column(name = "menu_date", nullable = false, columnDefinition = "date default current_date")
    private LocalDate menuDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Meal meal;

    @Column(name = "price", nullable = false)
    @NotNull
    private Integer price;

    public Menu(Menu menu) {
        this(menu.getId(), menu.getMenuDate(), menu.getRestaurant(), menu.getMeal(), menu.getPrice());
    }

    public Menu(Integer id, LocalDate menuDate, Restaurant restaurant, Meal meal, @NotNull Integer price) {
        super(id);
        setMenuDate(menuDate);
        this.restaurant = restaurant;
        this.meal = meal;
        this.price = price;
    }

    public Menu(Integer id, LocalDate menuDate, @NotNull Integer price) {
        super(id);
        setMenuDate(menuDate);
        this.price = price;
    }

    public void setMenuDate(LocalDate menuDate) {
        if (menuDate == null) menuDate = getCurrentDate();
        this.menuDate = menuDate;
    }
}
