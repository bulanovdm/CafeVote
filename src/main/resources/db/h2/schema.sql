DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS day_menus;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS restaurants;
DROP SEQUENCE IF EXISTS global_seq;

CREATE TABLE USERS
(
    id         INTEGER IDENTITY PRIMARY KEY,
    first_name       VARCHAR(255)            NOT NULL,
    last_name       VARCHAR(255)            NOT NULL,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON USERS (email);

CREATE TABLE USER_ROLE
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE RESTAURANTS
(
    id   INTEGER IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX restaurants_unique_name_idx ON restaurants (name);

CREATE TABLE MEALS
(
    id            INTEGER IDENTITY PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    restaurant_id INTEGER      NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX dishes_unique_restaurantid_mealname_idx ON MEALS (restaurant_id, name);

CREATE TABLE MENUS
(
    id            INTEGER IDENTITY PRIMARY KEY,
    menu_date      DATE DEFAULT current_date NOT NULL,
    restaurant_id INTEGER                   NOT NULL,
    meal_id       INTEGER                   NOT NULL,
    price         INTEGER                   NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE,
    FOREIGN KEY (meal_id) REFERENCES MEALS (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX daymenus_unique_menuday_restaurantid_mealid_idx ON MENUS (menu_date, restaurant_id, meal_id);

CREATE TABLE VOTES
(
    id            INTEGER IDENTITY PRIMARY KEY,
    vote_day      DATE    NOT NULL,
    user_id       INTEGER NOT NULL,
    restaurant_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX votes_unique_voteday_userid_idx ON votes (vote_day, user_id);