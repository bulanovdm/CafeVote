package com.topjava.CafeVote.to;

import com.topjava.CafeVote.model.Menu;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class MenuTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    protected LocalDate menuDate;

    @NotNull
    protected Integer price;

    public MenuTo(Integer id, @NotNull LocalDate menuDate, @NotNull Integer price) {
        super(id);
        this.menuDate = menuDate;
        this.price = price;
    }

    public MenuTo(Menu menu) {
        this(menu.getId(), menu.getMenuDate(), menu.getPrice());
    }
}
