package com.topjava.CafeVote.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.topjava.CafeVote.HasId;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(
        columnNames = {"menu_date", "restaurant_id", "meal_id"},
        name = "menus_unique_menuday_restaurantid_mealid_idx")})
@Getter @Setter @ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Menu extends AbstractBaseEntity {

    @Column(name = "menu_date", nullable = false, columnDefinition = "date default current_date")
    private LocalDate menuDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;

    //@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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
        if (menuDate == null) menuDate = LocalDate.now();
        this.menuDate = menuDate;
    }
}
