package project.smartlocker.repository.mappers

import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import org.postgis.PGgeography
import org.postgis.PGgeometry
import project.smartlocker.domain.module.*
import project.smartlocker.domain.module.ModuleEnum
import project.smartlocker.domain.module.ModuleStatus
import java.sql.ResultSet
import org.postgis.Point
import project.smartlocker.http.models.module.ModuleAppDTO

class ModuleMapper(): RowMapper<Module> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): Module {
        val geo = rs.getObject("module_location") as PGgeography
        val point = geo.geometry as Point
        val location = Location(point.y, point.x) // latitude, longitude

        return Module(
            rs.getInt("module_id"),
            location,
            rs.getInt("module_n")
        )
    }
}

class ModuleStatusMapper(): RowMapper<ModuleStatus> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): ModuleStatus {
        return ModuleStatus(
            rs.getInt("module"),
            rs.getString("module_location_name"),
            ModuleEnum.valueOf(rs.getString("module_status"))
        )
    }
}

class ModuleAppMapper(): RowMapper<ModuleAppDTO> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): ModuleAppDTO {
        val geo = rs.getObject("module_location") as PGgeography
        val point = geo.geometry as Point
        val location = Location(point.y, point.x) // latitude, longitude

        return ModuleAppDTO(
            rs.getInt("module_id"),
            location,
            rs.getInt("module_n"),
            rs.getString("module_location_name"),
            rs.getString("module_status"),
            rs.getInt("available_lockers")
        )
    }
}
