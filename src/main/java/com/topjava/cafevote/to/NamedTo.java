package com.topjava.cafevote.to;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public abstract class NamedTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 128)
    protected String name;

    public NamedTo(Integer id, @NotBlank @Size(min = 2, max = 128) String name) {
        super(id);
        this.name = name;
    }
}
