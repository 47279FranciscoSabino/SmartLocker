INSERT INTO LOCKER(locker_module, locker_hash, locker_active)
VALUES (1, '82cbf9b6f27c46c98a91cfa20a674c49', true ),
       (1, 'qr-qwert', false ),
       (2,'qw-qwerty', true ),
       (2, 'qw-qwertyu', false),
       (2, 'qr-qwertyuiop', true),
       (3, 'qwertyui', true),
       (4, 'qwertyuiop', true);


INSERT INTO LOCKER_STATUS(locker, locker_status)
VALUES (1,'IN_USE'),
       (2,'AVAILABLE'),
       (3,'MAINTENANCE'),
       (4, 'AVAILABLE' ),
       (5, 'AVAILABLE'),
       (6, 'AVAILABLE'),
       (7, 'AVAILABLE');

