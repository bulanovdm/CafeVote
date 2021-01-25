package com.topjava.CafeVote.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.topjava.CafeVote.util.DateTimeUtil;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"vote_day", "user_id"}, name = "votes_unique_vote_day_userid_idx")})
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Vote extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
    @JsonFormat(pattern = DateTimeUtil.DATE_PATTERN)
    @Column(name = "vote_day", nullable = false)
    @NotNull
    private LocalDate voteDate;

    public Vote(Integer id, @NotNull LocalDate voteDate, User user, Restaurant restaurant) {
        super(id);
        this.voteDate = voteDate;
        this.user = user;
        this.restaurant = restaurant;
    }

    public Vote(Vote vote) {
        this(vote.getId(), vote.getVoteDate(), vote.getUser(), vote.getRestaurant());
    }
}
