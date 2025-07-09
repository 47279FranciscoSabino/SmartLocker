INSERT INTO FRIENDS(user_locker, friend, friend_date)
VALUES (1,2,'2025-05-25'),
       (1,3,null),
       (2,3, '2020-01-15');

INSERT INTO FRIENDS_STATUS(user_locker, friend, friends_status)
VALUES (1,2,'ACCEPTED'),
       (1,3,'PENDING'),
       (2,3,'BLOCKED')