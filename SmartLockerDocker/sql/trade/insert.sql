INSERT INTO TRADE(trade_sender, trade_receiver, trade_locker,  trade_startdate, trade_enddate)
VALUES (1, 2, 1,'2025-02-02', NULL),
       (1, 3, 2, '2025-02-02', NULL),
       (4, 1, 3, '2025-04-10', NULL),
       (2, 1, 2, '2025-04-08', '2025-05-09'),
       (1, 4, 4, '2025-01-01', '2025-05-01'),
       (1, 2, 1, '2025-02-02', '2025-02-14'),
       (3, 1, 2, '2025-05-08', '2025-05-13');

INSERT INTO TRADE_STATUS(trade, trade_read, trade_status)
VALUES (1, true, 'PENDING' ),
       (2, false, 'PENDING'),
       (3, false, 'PENDING'),
       (4, true,  'COMPLETED'),
       (5,false, 'CANCELLED'),
       (6, true,  'COMPLETED'),
       (7,false, 'COMPLETED');

