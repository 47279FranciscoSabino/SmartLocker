CREATE OR REPLACE FUNCTION insert_trade()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
    BEGIN
        INSERT INTO trade_status(trade, trade_read, trade_status)
        VALUES (NEW.trade_id, false,  'PENDING');
        RETURN NEW;
    END;
$$;

CREATE TRIGGER insert_trade_trigger
AFTER INSERT ON trade
FOR EACH ROW
EXECUTE FUNCTION insert_trade();