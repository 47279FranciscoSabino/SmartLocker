package project.smartlocker.repository.mappers

import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import project.smartlocker.domain.user.User
import project.smartlocker.domain.user.UserEnum
import project.smartlocker.domain.user.UserStatus
import java.sql.ResultSet

class UserMapper(): RowMapper<User> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): User {
        return User(
            rs.getInt("user_id"),
            rs.getString("user_name"),
            rs.getString("user_email"),
            rs.getString("user_password")
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