begin transaction;


DELETE FROM USER_LOCKER;
DELETE FROM LOCKER;
DELETE FROM TRADE;


commit;

/*
begin transaction ;

DELETE FROM trade_status;
DELETE FROM trade;
DELETE FROM friends_status;
DELETE FROM friends;
DELETE FROM user_status;
DELETE FROM locker_status;
DELETE FROM locker;
DELETE FROM user_locker;
DELETE FROM module;

commit;
*/