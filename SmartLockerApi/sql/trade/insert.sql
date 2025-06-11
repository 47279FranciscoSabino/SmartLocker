INSERT INTO TRADE(trade_sender, trade_receiver, trade_locker,  trade_startdate, trade_enddate)
VALUES (1, 2, 1,'02-02-2025', NULL),
       (1, 3, 2, '02-02-2025', NULL),
       (4, 1, 3, '10-04-2025', NULL),
       (2, 1, 2, '08-04-2025', '09-05-2025'),
       (1, 4, 4, '01-01-2025', '01-05-2025'),
       (1, 2, 1, '02-02-2025', '14-02-2025'),
       (3, 1, 2, '08-05-2025', '13-05-2025');

INSERT INTO TRADE_STATUS(trade, trade_read, trade_status)
VALUES (1, true, 'PENDING' ),
       (2, false, 'PENDING'),
       (3, false, 'PENDING'),
       (4, true,  'COMPLETED'),
       (5,false, 'CANCELLED'),
       (6, true,  'COMPLETED'),
       (7,false, 'COMPLETED');

