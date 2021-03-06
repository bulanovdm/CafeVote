package com.topjava.cafevote.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonBackReference
    private List<Menu> menus;

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Integer id, String name, List<Menu> dayMenus) {
        super(id, name);
        this.menus = dayMenus;
    }

    public Restaurant(Restaurant restaurant) {
        super(restaurant.getId(), restaurant.getName());
        this.setMenus(restaurant.getMenus());
    }
}
