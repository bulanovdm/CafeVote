package com.topjava.CafeVote.util;

import com.topjava.CafeVote.model.Role;
import com.topjava.CafeVote.model.User;
import com.topjava.CafeVote.to.UserTo;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collections;

import static com.topjava.CafeVote.config.security.WebSecurityConfig.PASSWORD_ENCODER;


@UtilityClass
public class UserUtil {

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getEmail().toLowerCase(), userTo.getFirstName(), userTo.getLastName(),
                userTo.getPassword(), LocalDateTime.now(), Collections.singleton(Role.USER));
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setFirstName(userTo.getFirstName());
        user.setLastName(userTo.getLastName());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static User prepareToSave(User user) {
        String password = user.getPassword();
        user.setPassword(StringUtils.hasText(password) ? PASSWORD_ENCODER.encode(password) : password);
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
