package project.smartlocker.repository.mappers

import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import project.smartlocker.domain.friends.Friends
import project.smartlocker.domain.friends.FriendsEnum
import project.smartlocker.domain.friends.FriendsStatus
import project.smartlocker.http.models.friends.output.AdminFriendDTO
import project.smartlocker.http.models.friends.output.FriendDTO
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

class AdminFriendDTOMapper(): RowMapper<AdminFriendDTO> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): AdminFriendDTO {
        return AdminFriendDTO(
            rs.getInt("user_locker"),
            rs.getInt("friend"),
            rs.getString("friend_date"),
            rs.getString("friends_status")
        )
    }
}

class FriendDTOMapper(): RowMapper<FriendDTO> {
    //@Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): FriendDTO {
        return FriendDTO(
            rs.getInt("user_id"),
            rs.getString("user_name"),
            rs.getString("friend_date"),
            rs.getInt("days"),
            rs.getString("friends_status")
        )
    }
}

