package com.topjava.cafevote.to;

import com.topjava.cafevote.model.Meal;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Value
@EqualsAndHashCode(callSuper = true)
public class MealTo extends NamedTo implements Serializable {
    private static final long serialVersionUID = 1L;

    public MealTo(Meal meal) {
        this(meal.getId(), meal.getName());
    }

    public MealTo(Integer id, @NotBlank @Size(min = 2, max = 128) String name) {
        super(id, name);
    }
}
