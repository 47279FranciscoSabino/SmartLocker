package project.smartlocker.repository

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository
import project.smartlocker.domain.module.ModuleStatus
import project.smartlocker.domain.trade.Trade
import project.smartlocker.domain.trade.TradeStatus
import project.smartlocker.http.models.trade.TradeDTO


@Repository
interface TradeRepository {
    @SqlQuery(
        """
        SELECT * FROM trade
        """
    )
    fun getAllTrades(): List<Trade>

    @SqlQuery(
        """
        SELECT * FROM trade WHERE trade_id = :trade_id
        """
    )
    fun getTradeById(
        @Bind("trade_id") id: Int
    ): Trade?

    @SqlUpdate(
        """
        INSERT INTO trade (trade_sender, trade_receiver, trade_locker, trade_startdate)
        VALUES (:trade_sender, :trade_receiver, :trade_locker, :trade_startdate)
        """
    )
    @GetGeneratedKeys
    fun createTrade(
        @Bind("trade_sender") senderId: Int,
        @Bind("trade_receiver") receiverId: Int,
        @Bind("trade_locker") lockerId: Int,
        @Bind("trade_startdate") startingDate: String
    ):Int

    @SqlUpdate(
        """
        UPDATE trade 
        SET trade_sender = :trade_sender, 
            trade_receiver = :trade_receiver,
            trade_locker = :trade_locker,
            trade_startdate = :trade_startdate,
            trade_enddate = :trade_enddate
        WHERE trade_id = :trade_id
    
        """
    )
    @GetGeneratedKeys
    fun updateTrade(
        @Bind("trade_id") id: Int,
        @Bind("trade_sender") senderId: Int,
        @Bind("trade_receiver") receiverId: Int,
        @Bind("trade_locker") lockerId: Int,
        @Bind("trade_startdate") startingDate: String,
        @Bind("trade_enddate") endingDate: String?
    ):Int

    @SqlUpdate(
        """
        DELETE FROM trade WHERE trade_id = :trade_id
        """
    )
    fun deleteTrade(
        @Bind("trade_id") id: Int
    )

    // Status
    @SqlQuery(
        """
        SELECT * FROM trade_status WHERE trade = :trade
        """
    )
    fun getTradeStatus(
        @Bind("trade") trade: Int
    ): TradeStatus?

    @SqlUpdate(
        """
        INSERT INTO trade_status (trade, trade_status, trade_read)
        VALUES (:trade, :trade_status, :trade_read)
        """
    )
    fun createTradeStatus(
        @Bind("trade") trade: Int,
        @Bind("trade_read") read: Boolean,
        @Bind("trade_status") status: String
    )

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

    @SqlUpdate(
        """
        DELETE FROM trade_status WHERE trade = :trade
        """
    )
    @GetGeneratedKeys
    fun deleteTradeStatus(
        @Bind("trade") trade: Int
    ):Int





    @SqlQuery(
        """
        SELECT * FROM trade WHERE trade_locker = :trade_locker
        """
    )
    fun getTradesByLocker(
        @Bind ("trade_locker")locker: Int
    ): List<Trade>

    @SqlQuery(
        """
        SELECT * FROM trade WHERE trade_receiver = :trade_receiver
        """
    )
    fun getTradesByReceiver(
        @Bind ("trade_receiver") receiver: Int
    ): List<Trade>

    @SqlQuery(
        """
        SELECT * FROM trade WHERE trade_sender = :trade_sender
        """
    )
    fun getTradesBySender(
        @Bind("trade_sender") sender: Int
    ): List<Trade>

    @SqlQuery(
        """
        SELECT * FROM trade WHERE trade_sender = :user OR trade_receiver = :user
        """
    )
    fun getUserTrades(
        @Bind("user") user: Int
    ): List<Trade>

}
// 1 history a receber dos 2 sender or receiver
