CREATE OR REPLACE FUNCTION update_trade()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
    BEGIN
        UPDATE trade
        SET trade_enddate = CURRENT_DATE
        WHERE trade_id = NEW.trade;
        RETURN NEW;
    END;
$$ ;

CREATE TRIGGER update_trade_trigger
AFTER UPDATE ON trade_status
FOR EACH ROW
WHEN (OLD.trade_status IS DISTINCT FROM NEW.trade_status)
EXECUTE FUNCTION update_trade();