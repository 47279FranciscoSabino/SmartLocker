INSERT INTO USER_LOCKER (user_email, user_name, user_password)
VALUES
    ('a47279@alunos.isel.pt', 'admin','$2a$10$IIn/kQN4Xic53/SEe1CmB.Kop809MTtWaVSZMvLJ1IAgQ9UBvpw0q'),
    ('test@gmail.com','test', '$2a$10$IIn/kQN4Xic53/SEe1CmB.Kop809MTtWaVSZMvLJ1IAgQ9UBvpwrg'),
    ('bbb@hj.com', 'bob', '$2a$10$IIn/kQN4Xic53/SEe1CmB.Kop809MTtWaVSZMvLJ1IAgQ9UBvpwyh'),
    ('user1@test.com', 'user1', '$2a$10$IIn/kQN4Xic53/SEe1CmB.Kop809MTtWaVSZMvLJ1IAgQ9UBvpwol');

INSERT INTO USER_STATUS(user_locker, user_role, user_status)
VALUES (1, 'ADMIN','VERIFIED'),
       (2, 'STANDARD', 'NOT_VERIFIED'),
       (3, 'STANDARD','VERIFIED' ),
       (4, 'STANDARD','SUSPENDED');
