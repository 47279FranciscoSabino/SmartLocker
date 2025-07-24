package project.smartlocker.repository.mappers

import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import project.smartlocker.domain.trade.Trade
import project.smartlocker.domain.trade.TradeEnum
import project.smartlocker.domain.trade.TradeStatus
import project.smartlocker.http.models.trade.output.TradeDTO
import project.smartlocker.http.models.trade.output.TradeInfoDTO
import java.sql.ResultSet

class TradeMapper(): RowMapper<Trade> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): Trade {
        return Trade(
            rs.getInt("trade_id"),
            rs.getInt("trade_sender"),
            rs.getInt("trade_receiver"),
            rs.getInt("trade_locker"),
            rs.getString("trade_startdate"),
            rs.getString("trade_enddate")
        )
    }
}

class TradeStatusMapper(): RowMapper<TradeStatus> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): TradeStatus {
        return TradeStatus(
            rs.getInt("trade"),
            rs.getBoolean("trade_read"),
            TradeEnum.valueOf(rs.getString("trade_status"))
        )
    }
}

class TradeDTOMapper(): RowMapper<TradeDTO> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): TradeDTO {
        return TradeDTO(
            rs.getInt("trade_id"),
            rs.getInt("trade_sender"),
            rs.getInt("trade_receiver"),
            rs.getInt("trade_locker"),
            rs.getString("trade_startdate"),
            rs.getString("trade_enddate"),
            rs.getBoolean( "trade_read"),
            rs.getString("trade_status")
        )
    }
}

class TradeInfoDTOMapper(): RowMapper<TradeInfoDTO> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): TradeInfoDTO {
        return TradeInfoDTO(
            rs.getInt("trade_id"),
            rs.getInt("trade_sender"),
            rs.getInt("trade_receiver"),
            rs.getInt("trade_locker"),
            rs.getString("trade_startdate"),
            rs.getString("trade_enddate"),
            rs.getBoolean( "trade_read"),
            rs.getString("trade_status"),
            rs.getString("module_location_name")
        )
    }
}