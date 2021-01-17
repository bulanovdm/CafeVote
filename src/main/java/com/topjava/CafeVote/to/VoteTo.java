package com.topjava.CafeVote.to;

import com.topjava.CafeVote.model.Vote;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    protected LocalDate menuDate;

    @NotBlank
    @Size(min = 2, max = 100)
    protected String restaurantName;

    @Email
    @NotBlank
    @Size(min = 2, max = 100)
    protected String userEmail;

    public VoteTo(Integer id, @NotNull LocalDate menuDate, @NotBlank @Size(min = 2, max = 100) String restaurantName,
                  @Email @NotBlank @Size(min = 2, max = 100) String userEmail) {
        super(id);
        this.menuDate = menuDate;
        this.restaurantName = restaurantName;
        this.userEmail = userEmail;
    }

    public VoteTo(Vote vote) {
        this(vote.getId(), vote.getVoteDate(), vote.getRestaurant().getName(), vote.getUser().getEmail());
    }
}
