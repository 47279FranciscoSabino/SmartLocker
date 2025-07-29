CREATE OR REPLACE FUNCTION insert_locker()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
    BEGIN
        INSERT INTO locker_status(locker, locker_status)
        VALUES (NEW.locker_id, 'AVAILABLE');
        RETURN NEW;
    END;
$$;

CREATE TRIGGER insert_locker_trigger
    AFTER INSERT ON locker
    FOR EACH ROW
EXECUTE FUNCTION insert_locker();