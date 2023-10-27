DELETE FROM user_role;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2023-08-26 12:00:00', 'Meal1', 1000),
       (100001, '2023-08-26 12:01:00', 'Meal2', 2000),
       (100000, '2023-08-26 12:05:00', 'Meal3', 500);
