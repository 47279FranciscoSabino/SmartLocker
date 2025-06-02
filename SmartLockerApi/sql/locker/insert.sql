INSERT INTO LOCKER(locker_module, locker_qr, locker_active)
VALUES (1, 'qr-qwer', true ),
       (1, 'qr-qwert', false ),
       (2,'qw-qwerty', true ),
       (2, 'qw-qwertyu', false),
       (2, 'qr-qwertyuiop', true);

INSERT INTO LOCKER_STATUS(locker, status)
VALUES (1,'IN_USE'),
       (2,'AVAILABLE'),
       (3,'MAINTENANCE'),
       (4, 'AVAILABLE' ),
       (5, 'AVAILABLE')

