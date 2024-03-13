## 1. CREATE USER test WITH PASSWORD '1303';
                                    Список ролей
 Имя роли |                                Атрибуты                                 
----------+-------------------------------------------------------------------------
 postgres | Суперпользователь, Создаёт роли, Создаёт БД, Репликация, Пропускать RLS
 test     | 

## 2.CREATE TABLE users ( 
    user_id SERIAL PRIMARY KEY,
     email VARCHAR(255) NOT NULL, 
    name VARCHAR(100),
    lastname VARCHAR(100)
);
\dt
          Список отношений
 Схема  |  Имя  |   Тип   | Владелец 
--------+-------+---------+----------
 public | test  | таблица | postgres
 public | users | таблица | postgres
(2 строки)

## 3. INSERT INTO users (email, name, lastname) VALUES 
('user1@example.com', 'John', 'Doe'),
('user2@example.com', 'Alice', 'Smith'),
('user3@example.com', 'Bob', 'Johnson');
\o output.txt
SELECT * FROM users;
 user_id |       email       | name  | lastname 
---------+-------------------+-------+----------
       1 | user1@example.com | John  | Doe
       2 | user2@example.com | Alice | Smith
       3 | user3@example.com | Bob   | Johnson
(3 строки)

## 4. UPDATE users
SET name = 'Jane'
WHERE email = 'user1@example.com';

SELECT * FROM users;
UPDATE 1
 user_id |       email       | name  | lastname 
---------+-------------------+-------+----------
       2 | user2@example.com | Alice | Smith
       3 | user3@example.com | Bob   | Johnson
       1 | user1@example.com | Jane  | Doe
(3 строки)

## 5. DELETE FROM users
WHERE email = 'user2@example.com';

SELECT * FROM users;

DELETE 1
 user_id |       email       | name | lastname 
---------+-------------------+------+----------
       3 | user3@example.com | Bob  | Johnson
       1 | user1@example.com | Jane | Doe
(2 строки)

## 6. SELECT *
FROM users
WHERE lastname = 'Johnson';

 user_id |       email       | name | lastname 
---------+-------------------+------+----------
       3 | user3@example.com | Bob  | Johnson
(1 строка)




