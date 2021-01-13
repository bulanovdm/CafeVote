package com.topjava.CafeVote;

import com.topjava.CafeVote.model.Role;
import com.topjava.CafeVote.model.User;
import com.topjava.CafeVote.util.JsonUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = TestMatcher.usingIgnoringFieldsComparator(User.class, "registered", "password", "date");

    public static final int ADMIN_ID = 1;
    public static final int USER_ID = 2;
    public static final int NOT_FOUND = 100;

    public static final User ADMIN = new User(ADMIN_ID, "admin@gmail.com", "admin", "admin","password", Role.ADMIN, Role.USER);
    public static final User USER1 = new User(USER_ID, "user@gmail.com", "user", "user", "password", Role.USER);
    public static final User USER2 = new User(3, "frodo@mail.ru", "frodo", "baggins", "password", Role.USER);
    public static final User USER3 = new User(4, "sam@gmail.com", "sam", "gamgee", "password", Role.USER);
    public static final User USER4 = new User(5, "bilbo@gmail.com", "bilbo", "baggins", "password", Role.USER);
    public static final User USER5 = new User(6, "gendalf@yandex.ru", "gendalf", "gray", "password", Role.USER);
    public static final User USER6 = new User(7, "aragorn@gmail.com", "aragorn", "elessar", "password", Role.USER);
    public static final User USER7 = new User(8, "legolas@mail.ru", "legolas", "greenleaf", "password", Role.USER);
    public static final User USER8 = new User(9, "gimley@mail.ru", "gimley", "gloins", "password", Role.USER);

    public static final List<User> ALL_USERS = List.of(ADMIN, USER1, USER2, USER3, USER4, USER5, USER6, USER7, USER8);

    public static User getNew() {
        return new User(null, "new@gmail.com", "New", "User",  "newPass", LocalDateTime.now(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(USER1);
        updated.setFirstName("UpdatedFirstName");
        updated.setLastName("UpdatedLastName");
        updated.setPassword("newPass");
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}

