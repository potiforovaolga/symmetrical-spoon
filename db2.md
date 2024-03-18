##1. \c test
\o output.txt
CREATE TABLE users (
    userId SERIAL PRIMARY KEY,
    userName VARCHAR(50),
    userLastname VARCHAR(50)
);
CREATE TABLE chats (
    chatId SERIAL PRIMARY KEY,
    chatName VARCHAR(50)
);
CREATE TABLE messages (
    messageId SERIAL PRIMARY KEY,
    chatId INT,
    userId INT,
    messageText TEXT,
    messageDate TIMESTAMP
);
\dt
            Список отношений
 Схема  |   Имя    |   Тип   | Владелец 
--------+----------+---------+----------
 public | chats    | таблица | postgres
 public | messages | таблица | postgres
 public | users    | таблица | postgres
(3 строки)

##2. INSERT INTO users (userName, userLastname) VALUES
('Jane', 'Smith'),
('John', 'Doe'),
('Alice', 'Smith'),
('Bob', 'Johnson');INSERT 0 4
INSERT 0 4
SELECT * FROM users;
 userid | username | userlastname 
--------+----------+--------------
      1 | John     | Doe
      2 | Jane     | Smith
      3 | Alice    | Smith
      4 | Bob      | Johnson
(4 строки)

##3. INSERT INTO chats (chatName) VALUES ('Support'),
('Support'),
('Family Chat'),
('Work Chat');
INSERT 0 4
SELECT * FROM chats;
 chatid |  chatname   
--------+-------------
      1 | General
      2 | Support
      3 | Family Chat
      4 | Work Chat
(4 строки)

##4. INSERT INTO messages (chatId, userId, messageText, messageDate) VALUES (1, 1, 'Hello!', '2022-01-01 12:00:00');
(1, 2, 'Hi there!', '2022-01-02 10:00:00');
(1, 1, 'Hello everyone!', '2022-01-15 10:30:00'),
(2, 1, 'Hi Alice and Bob!', '2022-01-15 11:00:00');

INSERT 0 4
SELECT * FROM messages;
 messageid | chatid | userid |    messagetext    |     messagedate     
-----------+--------+--------+-------------------+---------------------
         1 |      1 |      1 | Hello!            | 2022-01-01 12:00:00
         2 |      1 |      2 | Hi there!         | 2022-01-02 10:00:00
         3 |      1 |      1 | Hello everyone!   | 2022-01-15 10:30:00
         4 |      1 |      2 | Hi Alice and Bob! | 2022-01-15 11:00:00
(4 строки)

##5. запрос для фильтрации данных в выбранных столбцах из трёх таблиц, он выбирает только те строки, у которых дата сообщения равна максимальной дате сообщения в данном чате.
SELECT u.userId, u.userName, u.userLastname, c.chatId, c.chatName, m.messageId AS lastMessageId, m.messageText AS lastMessageText
FROM users u
JOIN messages m ON u.userId = m.userId
JOIN chats c ON m.chatId = c.chatId
WHERE m.messageDate = (SELECT MAX(messageDate) FROM messages WHERE chatId = c.chatId)
GROUP BY u.userId, u.userName, u.userLastname, c.chatId, c.chatName, m.messageId, m.messageText;
 userid | username | userlastname | chatid | chatname | lastmessageid |  lastmessagetext  
--------+----------+--------------+--------+----------+---------------+-------------------
      2 | Jane     | Smith        |      1 | General  |             4 | Hi Alice and Bob!
(1 строка)

##6. Запрос который выбирает информацию о пользователях, чатах и количестве сообщений в каждом чате:
SELECT u.userId, u.userName, u.userLastname, c.chatId, c.chatName, COUNT(m.messageId) AS messageCount
FROM users u 
JOIN messages m ON u.userId = m.userId 
JOIN chats c ON m.chatId = c.chatId
GROUP BY u.userId, u.userName, u.userLastname, c.chatId, c.chatName
ORDER BY messageCount DESC;
 userid | username | userlastname | chatid | chatname | messagecount 
--------+----------+--------------+--------+----------+--------------
      1 | John     | Doe          |      1 | General  |            2
      2 | Jane     | Smith        |      1 | General  |            2
(2 строки)



