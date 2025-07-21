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
        WHERE f.friend = :friend AND f.user_locker = :user
        """
    )
    fun getFriend(
        @Bind("user") user: Int,
        @Bind("friend") friend: Int
    ): AdminFriendDTO?
    /*

    //global
    @SqlQuery(
        """
        SELECT
        u.*,
        f.friend_date,
        (CURRENT_DATE - f.friend_date) as days,
        fs.friends_status
        FROM friends f
        INNER JOIN friends_status fs ON fs.friend = f.friend AND fs.user_locker = f.user_locker
        INNER JOIN user_locker u
        ON (f.user_locker = :user AND u.user_id = f.friend)
        OR (f.friend = :user AND u.user_id = f.friend)
        WHERE f.user_locker = :user OR f.friend = :user
        """
    )
    fun getAllFriendsInfo(
        @Bind("user") user: Int
    ): List<FriendDTO>

    @SqlUpdate(
        """
        INSERT INTO friends (user_locker, friend)
        VALUES (:user, :friend)
        """
    )
    fun createFriend(
        @Bind("user") user: Int,
        @Bind("friend") friend: Int
    )

    @SqlUpdate(
        """
        UPDATE friends_status
        SET friends_status = :status
        WHERE user_locker = :user AND friend = :friend
        """
    )
    fun updateFriend(
        @Bind("user") user: Int,
        @Bind("friend") friend: Int,
        @Bind("status") status: String
    )

    @SqlUpdate(
        """
        DELETE FROM friends WHERE user_locker = :user AND friend = :friend
        """
    )
    fun deleteFriends(
        @Bind("user") user: Int,
        @Bind("friend") friend: Int
    )

     */

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
        WHERE user_locker = :user AND friend = :friend
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
        DELETE FROM friends WHERE user_locker = :user AND friend = :friend  
        """
    )
    fun removeFriend(
        @Bind("user") user: Int,
        @Bind("friend") friend: Int
    )
}