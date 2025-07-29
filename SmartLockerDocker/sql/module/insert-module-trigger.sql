CREATE OR REPLACE FUNCTION insert_module()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
    BEGIN
        INSERT INTO module_status(module, module_status)
        VALUES (NEW.module_id, 'AVAILABLE');
        RETURN NEW;
    END;
$$;

CREATE TRIGGER insert_module_trigger
AFTER INSERT ON module
FOR EACH ROW
EXECUTE FUNCTION insert_module();