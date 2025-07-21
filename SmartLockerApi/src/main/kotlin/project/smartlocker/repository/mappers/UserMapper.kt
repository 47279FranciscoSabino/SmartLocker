package project.smartlocker.repository.mappers

import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import project.smartlocker.domain.user.User
import project.smartlocker.domain.user.UserEnum
import project.smartlocker.domain.user.UserStatus
import project.smartlocker.http.models.user.output.AdminUserDTO
import project.smartlocker.http.models.user.output.AdminUserStatusDTO
import project.smartlocker.http.models.user.output.UserDTO
import java.sql.ResultSet

class UserMapper(): RowMapper<User> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): User {
        return User(
            rs.getInt("user_id"),
            rs.getString("user_name"),
            rs.getString("user_email"),
            rs.getString("user_password"),
            rs.getString("user_token")
        )
    }
}

class UserStatusMapper(): RowMapper<UserStatus> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): UserStatus {
        return UserStatus(
            rs.getInt("user_locker"),
            UserEnum.valueOf(rs.getString("user_status"))
        )
    }
}

class AdminUserDTOMapper(): RowMapper<AdminUserDTO> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): AdminUserDTO {
        return AdminUserDTO(
            rs.getInt("user_id"),
            rs.getString("user_name"),
            rs.getString("user_email"),
            rs.getString("user_password"),
            rs.getString("user_status")
        )
    }
}

class AdminUserStatusDTOMapper(): RowMapper<AdminUserStatusDTO> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): AdminUserStatusDTO {
        return AdminUserStatusDTO(
            rs.getInt("user_locker"),
            rs.getString("user_status")
        )
    }
}

class UserDTOMapper(): RowMapper<UserDTO> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): UserDTO {
        return UserDTO(
            rs.getInt("user_id"),
            rs.getString("user_name"),
            rs.getString("user_email"),
            rs.getString("user_status"),
            rs.getString("user_role")
        )
    }
}