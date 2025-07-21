package project.smartlocker.repository

import TradeInfoDTO
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.springframework.stereotype.Repository

@Repository
interface HistoryRepository {

// ------------------------------------------------------------------------------------
    @SqlQuery(
        """
        SELECT
        t.*,
        ts.trade_read,
        ts.trade_status,
        m.module_location_name
        FROM trade t
        INNER JOIN trade_status ts ON ts.trade = t.trade_id
        INNER JOIN locker l ON l.locker_id = t.trade_locker
        INNER JOIN module m ON m.module_id = l.locker_module
        WHERE (trade_sender = :user OR trade_receiver = :user)
        AND ts.trade_status != 'PENDING'
        """
    )
    fun getFullHistory(
        @Bind("user") user: Int
    ): List<TradeInfoDTO>


    @SqlQuery(
        """
        SELECT
        t.*,
        ts.trade_read,
        ts.trade_status,
        m.module_location_name
        FROM trade t
        INNER JOIN trade_status ts ON ts.trade = t.trade_id
        INNER JOIN locker l ON l.locker_id = t.trade_locker
        INNER JOIN module m ON m.module_id = l.locker_module
        WHERE trade_receiver = :trade_receiver
        AND ts.trade_status = 'PENDING'
        """
    )
    fun getReceiverHistory(
        @Bind("trade_receiver") receiver: Int
    ): List<TradeInfoDTO>

    @SqlQuery(
        """
        SELECT
        t.*,
        ts.trade_read,
        ts.trade_status,
        m.module_location_name
        FROM trade t
        INNER JOIN trade_status ts ON ts.trade = t.trade_id
        INNER JOIN locker l ON l.locker_id = t.trade_locker
        INNER JOIN module m ON m.module_id = l.locker_module
        WHERE trade_sender = :trade_sender
        AND ts.trade_status = 'PENDING'
        """
    )
    fun getSenderHistory(
        @Bind("trade_sender") sender: Int
    ): List<TradeInfoDTO>
}