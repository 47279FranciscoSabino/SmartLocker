package project.smartlocker.repository

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository
import project.smartlocker.http.models.friends.output.AdminFriendDTO
import project.smartlocker.http.models.friends.output.FriendDTO

@Repository
interface FriendsRepository {
    //admin
    @SqlQuery(
        """
        SELECT
        f.*,
        fs.friends_status
        FROM friends f
        INNER JOIN friends_status fs ON fs.friend = f.friend AND fs.user_locker = f.user_locker
        WHERE f.user_locker = :user OR f.friend = :user
        """
    )
    fun getAllFriends(
        @Bind("user") user: Int
    ): List<AdminFriendDTO>

    @SqlQuery(
        """        
        SELECT 
        f.*,
        fs.friends_status
        FROM friends f
        INNER JOIN friends_status fs ON fs.friend = f.friend AND fs.user_locker = f.user_locker
        WHERE (f.friend = :friend AND f.user_locker = :user) OR (f.friend = :user AND f.user_locker = :friend) 
        """
    )
    fun getFriend(
        @Bind("user") user: Int,
        @Bind("friend") friend: Int
    ): AdminFriendDTO?

// ------------------------------------------------------------------------------------
    //getFriendsList
    @SqlQuery(
        """
            SELECT 
              CASE 
                WHEN f.user_locker = :user THEN u2.user_id
                ELSE u1.user_id
              END AS user_id,
              
              CASE 
                WHEN f.user_locker = :user THEN u2.user_name
                ELSE u1.user_name
              END AS user_name,
              
              f.friend_date AS friend_date,
              (CURRENT_DATE - f.friend_date) AS days,
              fs.friends_status AS friends_status
            FROM friends f
            INNER JOIN friends_status fs ON fs.friend = f.friend AND fs.user_locker = f.user_locker
            INNER JOIN user_locker u1 ON f.user_locker = u1.user_id
            INNER JOIN user_locker u2 ON f.friend = u2.user_id
            WHERE f.user_locker = :user OR f.friend = :user
        """
    )
    fun getFriends(
        @Bind("user") user: Int
    ): List<FriendDTO>

    //addFriend
    @SqlUpdate(
        """
        INSERT INTO friends (user_locker, friend)
        VALUES (:user, :friend)
        """
    )
    fun addFriend(
        @Bind("user") user: Int,
        @Bind("friend") friend: Int
    )

    //editFriend
    @SqlUpdate(
        """
        UPDATE friends_status 
        SET friends_status = :status
        WHERE (friend = :friend AND user_locker = :user) OR (friend = :user AND user_locker = :friend) 
        """
    )
    fun editFriend(
        @Bind("user") user: Int,
        @Bind("friend") friend: Int,
        @Bind("status") status: String
    )

    //removeFriend
    @SqlUpdate(
        """
        DELETE FROM friends WHERE (friend = :friend AND user_locker = :user) OR (friend = :user AND user_locker = :friend) 
        """
    )
    fun removeFriend(
        @Bind("user") user: Int,
        @Bind("friend") friend: Int
    )
}