CREATE OR REPLACE FUNCTION insert_user()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
    BEGIN
        INSERT INTO user_status (user_locker, user_status)
        VALUES (NEW.user_id, 'NOT_VERIFIED');
        RETURN NEW;
    END;
$$ ;

CREATE TRIGGER insert_user_trigger
AFTER INSERT ON user_locker
FOR EACH ROW
EXECUTE FUNCTION insert_user();