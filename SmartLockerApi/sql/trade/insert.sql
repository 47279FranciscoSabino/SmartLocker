INSERT INTO TRADE(trade_sender, trade_receiver, trade_locker,  trade_startdate, trade_enddate)
VALUES (1, 2, 1,'02', '05'),
       (1, 3, 2, '85', '963' );

INSERT INTO TRADE_STATUS(trade, trade_read, trade_status)
VALUES (1, true, 'PENDING' ),
       (2, false, 'CANCELLED')

