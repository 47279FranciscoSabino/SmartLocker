---Trade
CREATE TABLE TRADE (
    trade_id SERIAL PRIMARY KEY,
    trade_sender int NOT NULL,
    trade_receiver int NOT NULL,
    trade_locker int NOT NULL,
    trade_startdate DATE NOT NULL,
    trade_enddate DATE,
    FOREIGN KEY (trade_sender) REFERENCES USER_LOCKER(user_id) ON DELETE CASCADE,
    FOREIGN KEY (trade_receiver) REFERENCES USER_LOCKER(user_id) ON DELETE CASCADE,
    FOREIGN KEY (trade_locker) REFERENCES LOCKER(locker_id) ON DELETE CASCADE
);


---Status   -- PENDING, COMPLETED, CANCELLED
CREATE TABLE Trade_Status (
    trade int PRIMARY KEY REFERENCES TRADE(trade_id) ON DELETE CASCADE,
    trade_read boolean,
    trade_status varchar(50)
);