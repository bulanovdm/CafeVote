package com.topjava.CafeVote.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.topjava.CafeVote.HasId;
import com.topjava.CafeVote.HasIdAndEmail;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"password"})
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends AbstractBaseEntity implements HasIdAndEmail {

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotEmpty
    @Size(max = 128)
    private String email;

    @Column(name = "first_name")
    @NotBlank
    @Size(min = 2, max = 100)
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    @Size(min = 2, max = 100)
    private String lastName;

    @Column(name = "password")
    @Size(max = 255)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    @OnDelete(action= OnDeleteAction.CASCADE)
    private Set<Role> roles;

    public void setEmail(String email) {
        this.email = StringUtils.hasText(email) ? email.toLowerCase() : null;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    public User(Integer id, @Email @NotEmpty @Size(max = 128) String email, @NotBlank @Size(min = 2, max = 100) String firstName,
                @NotBlank @Size(min = 2, max = 100) String lastName, @Size(max = 255) String password, @NotNull LocalDateTime date, Set<Role> roles) {
        super(id);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.date = date;
        setRoles(roles);
    }

    public User(User u) {
        this(u.getId(), u.getEmail(), u.getFirstName(), u.getLastName(),  u.getPassword(), u.getDate(), u.getRoles());
    }

    public User(Integer id, String email, String firstname, String lastname, String password, Role role, Role... roles) {
        this(id, email, firstname, lastname, password, LocalDateTime.now(), EnumSet.of(role, roles));
    }
}
