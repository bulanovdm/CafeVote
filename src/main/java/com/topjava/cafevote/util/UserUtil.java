package com.topjava.cafevote.util;

import com.topjava.cafevote.model.Role;
import com.topjava.cafevote.model.User;
import com.topjava.cafevote.to.UserTo;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.util.Collections;

import static com.topjava.cafevote.config.security.WebSecurityConfig.PASSWORD_ENCODER;
import static com.topjava.cafevote.util.DateTimeUtil.getCurrentDateTime;


@UtilityClass
public class UserUtil {

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getEmail().toLowerCase(), userTo.getFirstName(), userTo.getLastName(),
                userTo.getPassword(), getCurrentDateTime(), Collections.singleton(Role.USER));
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
