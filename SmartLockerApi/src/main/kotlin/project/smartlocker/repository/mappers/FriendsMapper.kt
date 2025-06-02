package project.smartlocker.repository.mappers

import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import project.smartlocker.domain.friends.Friends
import project.smartlocker.domain.friends.FriendsEnum
import project.smartlocker.domain.friends.FriendsStatus
import java.sql.ResultSet

class FriendsMapper(): RowMapper<Friends> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): Friends {
        return Friends(
            rs.getInt("user_locker"),
            rs.getInt("friend"),
            rs.getString("friend_date")
        )
    }
}

class FriendsStatusMapper(): RowMapper<FriendsStatus> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): FriendsStatus {
        return FriendsStatus(
            rs.getInt("user_locker"),
            rs.getInt("friend"),
            FriendsEnum.valueOf(rs.getString("friends_status"))
        )
    }
}