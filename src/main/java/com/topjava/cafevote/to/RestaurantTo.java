package com.topjava.cafevote.to;

import com.topjava.cafevote.model.Restaurant;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo implements Serializable {
    private static final long serialVersionUID = 1L;

    public RestaurantTo(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName());
    }

    public RestaurantTo(Integer id, @NotBlank @Size(min = 2, max = 128) String name) {
        super(id, name);
    }
}
