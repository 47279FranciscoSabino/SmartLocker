---Friends
CREATE TABLE FRIENDS(
    user_locker int,
    friend int,
    friend_date DATE,
    PRIMARY KEY (user_locker, friend),
    FOREIGN KEY (user_locker) REFERENCES USER_LOCKER(user_id) ON DELETE CASCADE,
    FOREIGN KEY (friend) REFERENCES USER_LOCKER(user_id) ON DELETE CASCADE
);

---Status   -- PENDING, ACCEPTED, BLOCKED
CREATE TABLE FRIENDS_STATUS(
    user_locker int,
    friend int,
    friends_status varchar(50),
    PRIMARY KEY (user_locker, friend),
    FOREIGN KEY (user_locker, friend) REFERENCES FRIENDS(user_locker, friend) ON DELETE CASCADE
);
