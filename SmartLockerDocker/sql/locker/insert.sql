INSERT INTO LOCKER(locker_module, locker_hash, locker_ip, locker_active)
VALUES (1, '82cbf9b6f27c46c98a91cfa20a674c49','192.168.1.1', true ),
       (1, 'qr-qwert', '10.0.0.5', false ),
       (2,'qw-qwerty', '172.16.0.10', true ),
       (2, 'qw-qwertyu', '8.8.8.8', false),
       (2, 'qr-qwertyuiop', '203.0.113.42',true),
       (3, 'qwertyui','192.0.2.123', true),
       (4, 'qwertyuiop','127.0.0.1', true);


INSERT INTO LOCKER_STATUS(locker, locker_status)
VALUES (1,'IN_USE'),
       (2,'AVAILABLE'),
       (3,'MAINTENANCE'),
       (4, 'AVAILABLE' ),
       (5, 'AVAILABLE'),
       (6, 'AVAILABLE'),
       (7, 'AVAILABLE');

