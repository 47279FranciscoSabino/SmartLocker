CREATE OR REPLACE FUNCTION update_friend()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
    BEGIN
        UPDATE friends
        SET friend_date = CURRENT_DATE
        WHERE user_locker = NEW.user_locker AND friend = NEW.friend;
        RETURN NEW;
    END;
$$ ;

CREATE TRIGGER update_friend_trigger
AFTER UPDATE ON friends_status
FOR EACH ROW
WHEN (OLD.friends_status IS DISTINCT FROM NEW.friends_status)
EXECUTE FUNCTION update_friend();