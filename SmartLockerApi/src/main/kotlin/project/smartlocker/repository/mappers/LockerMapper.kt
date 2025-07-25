package project.smartlocker.repository.mappers

import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import project.smartlocker.domain.locker.Locker
import project.smartlocker.domain.locker.LockerEnum
import project.smartlocker.domain.locker.LockerStatus
import project.smartlocker.http.models.locker.output.LockerDTO
import java.sql.ResultSet

class LockerMapper(): RowMapper<Locker> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): Locker {
        return Locker(
            rs.getInt("locker_id"),
            rs.getInt("locker_module"),
            rs.getString("locker_hash"),
            rs.getBoolean("locker_active")
        )
    }
}

class LockerStatusMapper(): RowMapper<LockerStatus> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): LockerStatus {
        return LockerStatus(
            rs.getInt("locker"),
            LockerEnum.valueOf(rs.getString("locker_status"))
        )
    }
}

class LockerDTOMapper(): RowMapper<LockerDTO> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): LockerDTO {
        return LockerDTO(
            rs.getInt("locker_id"),
            rs.getInt("locker_module"),
            rs.getString("locker_hash"),
            rs.getBoolean("locker_active"),
            rs.getString("locker_status"),
            rs.getString("locker_ip")
        )
    }
}