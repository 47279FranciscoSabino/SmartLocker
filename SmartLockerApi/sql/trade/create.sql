---Trade
CREATE TABLE TRADE (
    trade_id SERIAL PRIMARY KEY,
    trade_sender int NOT NULL,
    trade_receiver int NOT NULL,
    trade_locker int NOT NULL,
    trade_startdate varchar(40) NOT NULL,
    trade_enddate varchar(40),
    FOREIGN KEY (trade_sender) REFERENCES USER_LOCKER(user_id) ON DELETE CASCADE,
    FOREIGN KEY (trade_receiver) REFERENCES USER_LOCKER(user_id) ON DELETE CASCADE,
    FOREIGN KEY (trade_locker) REFERENCES LOCKER(locker_id) ON DELETE CASCADE
);


---Status
CREATE TABLE Trade_Status (
    trade int PRIMARY KEY REFERENCES TRADE(trade_id),
    trade_read boolean,
    trade_status varchar(50)
);