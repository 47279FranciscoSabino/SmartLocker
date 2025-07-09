CREATE OR REPLACE FUNCTION insert_friend()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
    BEGIN
        INSERT INTO friends_status (user_locker, friend, friends_status)
        VALUES (NEW.user_locker, NEW.friend, 'PENDING');
        RETURN NEW;
    END;
$$ ;

CREATE TRIGGER insert_friend_trigger
AFTER INSERT ON friends
FOR EACH ROW
EXECUTE FUNCTION insert_friend();