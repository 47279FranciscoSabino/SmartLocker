package project.smartlocker.repository.mappers

import com.example.smartlockerandroid.data.model.hash.output.QrScanDTO
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import project.smartlocker.http.models.locker.output.LockerDTO
import java.sql.ResultSet

class QrScanDTOMapper(): RowMapper<QrScanDTO> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): QrScanDTO {
        return QrScanDTO(
            rs.getString("hash"),
            rs.getInt("locker"),
            rs.getBytes("hash_qr")
        )
    }
}