package project.smartlocker.repository

import TradeInfoDTO
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository
import project.smartlocker.domain.trade.TradeStatus
import project.smartlocker.http.models.trade.output.TradeDTO


@Repository
interface TradeRepository {
    // admin
    @SqlQuery(
        """
        SELECT
        t.*,
        ts.trade_read,
        ts.trade_status
        FROM trade t 
        INNER JOIN trade_status ts ON ts.trade = t.trade_id
        """
    )
    fun getAllTrades(): List<TradeDTO>

    @SqlQuery(
        """
        SELECT * FROM trade_status
        """
    )
    fun getAllTradesStatus(): List<TradeStatus>

    @SqlUpdate(
        """
        DELETE FROM trade WHERE trade_id = :trade_id
        """
    )
    fun deleteTrade(
        @Bind("trade_id") id: Int
    )

    // global
    @SqlQuery(
        """
        SELECT
        t.*,
        ts.trade_read,
        ts.trade_status
        FROM trade t 
        INNER JOIN trade_status ts ON ts.trade = t.trade_id 
        WHERE trade_id = :trade_id
        """
    )
    fun getTradeById(
        @Bind("trade_id") id: Int
    ): TradeDTO?

    @SqlQuery(
        """
        SELECT 
        t.*,
        ts.trade_read,
        ts.trade_status
        FROM trade t 
        INNER JOIN trade_status ts ON ts.trade = t.trade_id
        WHERE trade_locker = :trade_locker AND trade_status = 'PENDING'
        """
    )
    fun getPendingTrade(
        @Bind("trade_locker") locker: Int
    ): TradeDTO?

    @SqlUpdate(
        """
        INSERT INTO trade (trade_sender, trade_receiver, trade_locker, trade_startdate)
        VALUES (:trade_sender, :trade_receiver, :trade_locker, CURRENT_DATE)
        """
    )
    @GetGeneratedKeys
    fun createTrade(
        @Bind("trade_sender") senderId: Int,
        @Bind("trade_receiver") receiverId: Int,
        @Bind("trade_locker") lockerId: Int
    ):Int

    @SqlUpdate(
        """
        UPDATE trade 
        SET trade_receiver = :trade_receiver
        WHERE trade_id = :trade_id
    
        """
    )
    @GetGeneratedKeys
    fun updateTrade(
        @Bind("trade_id") id: Int,
        @Bind("trade_receiver") receiverId: Int,
    ):Int

    @SqlUpdate(
        """
        UPDATE trade_status
        SET trade_status = :trade_status,
            trade_read = :trade_read
        WHERE trade = :trade
        """
    )
    fun updateTradeStatus(
        @Bind("trade") trade: Int,
        @Bind("trade_read") read: Boolean,
        @Bind("trade_status") status: String
    )



    @SqlQuery(
        """
        SELECT
        t.*,
        ts.trade_read,
        ts.trade_status
        FROM trade t
        INNER JOIN trade_status ts ON ts.trade = t.trade_id
        WHERE trade_locker = :trade_locker
        """
    )
    fun getTradesByLocker(
        @Bind ("trade_locker")locker: Int
    ): List<TradeDTO>

    @SqlQuery(
        """
        SELECT
        t.*,
        ts.trade_read,
        ts.trade_status
        FROM trade t
        INNER JOIN trade_status ts ON ts.trade = t.trade_id
        WHERE trade_receiver = :trade_receiver
        """
    )
    fun getTradesByReceiver(
        @Bind ("trade_receiver") receiver: Int
    ): List<TradeDTO>

    @SqlQuery(
        """
        SELECT
        t.*,
        ts.trade_read,
        ts.trade_status
        FROM trade t
        INNER JOIN trade_status ts ON ts.trade = t.trade_id
        WHERE trade_sender = :trade_sender
        """
    )
    fun getTradesBySender(
        @Bind("trade_sender") sender: Int
    ): List<TradeDTO>

    @SqlQuery(
        """
        SELECT
        t.*,
        ts.trade_read,
        ts.trade_status
        FROM trade t
        INNER JOIN trade_status ts ON ts.trade = t.trade_id
        WHERE trade_sender = :user OR trade_receiver = :user
        """
    )
    fun getUserTrades(
        @Bind("user") user: Int
    ): List<TradeDTO>

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
        WHERE trade_id = :id   
        """
    )
    fun getTrade(
        @Bind ("id")id: Int
    ): TradeInfoDTO?

}
