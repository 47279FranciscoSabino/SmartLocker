---User
CREATE TABLE USER_LOCKER (
    user_id SERIAL PRIMARY KEY,
    user_email varchar(255) UNIQUE,
    user_name varchar(50) NOT NULL,
    user_password varchar(255) NOT NULL
);

---Status   -- VERIFIED, NOT_VERIFIED, SUSPENDED
CREATE TABLE USER_STATUS (
    user_locker int PRIMARY KEY REFERENCES USER_LOCKER(user_id) ON DELETE CASCADE,
    user_status varchar(50)
);
