package project.smartlocker.repository

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository
import project.smartlocker.domain.friends.Friends
import project.smartlocker.domain.friends.FriendsStatus

@Repository
interface FriendsRepository {
    @SqlQuery(
        """
        SELECT * FROM friends WHERE user_locker = :user OR friend = :user
        """
    )
    fun getAllFriends(
        @Bind("user") user: Int
    ): List<Friends>

    @SqlQuery(
        """
        SELECT * FROM friends WHERE user_locker = :user AND friend = :friend
        """
    )
    fun getFriend(
        @Bind("user") user: Int,
        @Bind("friend") friend: Int
    ): Friends?

    @SqlUpdate(
        """
        INSERT INTO friends (user_locker, friend, friend_date)
        VALUES (:user, :friend, :date)
        """
    )
    fun createFriends(
        @Bind("user") user: Int,
        @Bind("friend") friend: Int,
        @Bind("date") date: String
    )

    @SqlUpdate(
        """
        UPDATE friends
        SET friend_date = :date
        WHERE user_locker = :user AND friend = :friend
        """
    )
    fun updateFriends(
        @Bind("user") user: Int,
        @Bind("friend") friend: Int,
        @Bind("date") date: String
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


    //Status
    @SqlQuery(
        """
        SELECT * FROM friends_status WHERE user_locker = :user AND friend = :friend
        """
    )
    fun getFriendsStatus(
        @Bind("user") user: Int,
        @Bind("friend") friend: Int,
    ): FriendsStatus

    @SqlUpdate(
        """
        INSERT INTO friends_status (user_locker, friend, friends_status)
        VALUES (:user, :friend, :status)
        """
    )
    fun createFriendsStatus(
        @Bind("user") user: Int,
        @Bind("friend") friend: Int,
        @Bind("status") status: String,
    )

    @SqlUpdate(
        """
        UPDATE friends_status SET friends_status = :status WHERE user_locker = :user AND friend = :friend
        """
    )
    fun updateFriendsStatus(
        @Bind("user") user: Int,
        @Bind("friend") friend: Int,
        @Bind("status") status: String
    )

    @SqlUpdate(
        """
        DELETE FROM friends_status WHERE user_locker = :user AND friend = :friend
        """
    )
    @GetGeneratedKeys
    fun deleteFriendsStatus(
        @Bind("user") user: Int,
        @Bind("friend") friend: Int,
    ):Int
}