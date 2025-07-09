package project.smartlocker.repository.mappers

import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import org.postgis.PGgeography
import project.smartlocker.domain.module.*
import project.smartlocker.domain.module.ModuleEnum
import project.smartlocker.domain.module.ModuleStatus
import java.sql.ResultSet
import org.postgis.Point
import project.smartlocker.http.models.module.output.AdminModuleDTO
import project.smartlocker.http.models.module.output.ModuleDTO

class ModuleMapper(): RowMapper<Module> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): Module {
        val geo = rs.getObject("module_location") as PGgeography
        val point = geo.geometry as Point
        val location = Location(point.y, point.x) // latitude, longitude

        return Module(
            rs.getInt("module_id"),
            location,
            rs.getString("module_location_name"),
            rs.getInt("module_n")
        )
    }
}

class ModuleStatusMapper(): RowMapper<ModuleStatus> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): ModuleStatus {
        return ModuleStatus(
            rs.getInt("module"),
            ModuleEnum.valueOf(rs.getString("module_status"))
        )
    }
}

class AdminModuleDTOMapper(): RowMapper<AdminModuleDTO> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): AdminModuleDTO {
        val geo = rs.getObject("module_location") as PGgeography
        val point = geo.geometry as Point
        val location = Location(point.y, point.x) // latitude, longitude

        return AdminModuleDTO(
            rs.getInt("module_id"),
            location,
            rs.getString("module_location_name"),
            rs.getInt("module_n"),
            rs.getString("module_status")
        )
    }
}

class ModuleAppMapper(): RowMapper<ModuleDTO> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): ModuleDTO {
        val geo = rs.getObject("module_location") as PGgeography
        val point = geo.geometry as Point
        val location = Location(point.y, point.x) // latitude, longitude

        return ModuleDTO(
            rs.getInt("module_id"),
            location,
            rs.getString("module_location_name"),
            rs.getInt("module_n"),
            rs.getString("module_status"),
            rs.getInt("available_lockers")
        )
    }
}
