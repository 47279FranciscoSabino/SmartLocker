INSERT INTO USER_LOCKER (user_email, user_name, user_password)
VALUES
    ('a47279@alunos.isel.pt', 'admin','admin'),
    ('test@gmail.com','test', 'test'),
    ('bbb@hj.com', 'bbb', 'big'),
    ('user1@test.com', 'user1', 'test');

INSERT INTO USER_STATUS(user_locker, user_role, user_status)
VALUES (1, 'ADMIN','VERIFIED'),
       (2, 'STANDARD', 'NOT_VERIFIED'),
       (3, 'STANDARD','VERIFIED' ),
       (4, 'STANDARD','SUSPENDED');
