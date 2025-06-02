package project.smartlocker.repository

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository
import project.smartlocker.domain.user.User
import project.smartlocker.domain.user.UserStatus


@Repository
interface UserRepository {

    @SqlQuery(
        """
        SELECT * FROM user_locker
        """
    )
    fun getAllUsers(): List<User>

    @SqlQuery(
        """
        SELECT * FROM user_locker WHERE user_id = :id 
        """
    )
    fun getUserById(
        @Bind("id") id: Int
    ): User?

    @SqlUpdate(
        """
        INSERT INTO user_locker (user_name, user_email, user_password)
        VALUES (:user_name, :user_email, :user_password)
        """
    )
    @GetGeneratedKeys
    fun createUser(
        @Bind("user_name") username: String,
        @Bind("user_email") email: String,
        @Bind("user_password") password: String
    ):Int

    @SqlUpdate(
        """
        UPDATE user_locker 
        SET user_name = :user_name,
            user_password = :user_password,
            user_email = :user_email
        WHERE user_id = :user_id
        """
    )
    @GetGeneratedKeys
    fun updateUser(
        @Bind("user_id") id: Int,
        @Bind("user_name") username: String,
        @Bind("user_email") email: String,
        @Bind("user_password") password: String
    ):Int

    @SqlUpdate(
        """
        DELETE FROM user_locker WHERE user_id = :user_id
        """
    )
    @GetGeneratedKeys
    fun deleteUser(
        @Bind("user_id") id: Int
    ):Int

    // STATUS
    @SqlQuery(
        """
        SELECT * FROM user_status WHERE user_locker = :user_locker
        """
    )
    fun getUserStatus(
        @Bind("user_locker") user_locker: Int
    ):UserStatus?

    @SqlUpdate(
        """
        INSERT INTO user_status (user_locker, user_status)
        VALUES (:user_locker, :user_status)
        """
    )
    fun createUserStatus(
        @Bind("user_locker") user: Int,
        @Bind("user_status") status: String
    )

    @SqlUpdate(
        """
        UPDATE user_status
        SET user_status = :user_status
        WHERE user_locker = :user_locker
        """
    )
    fun updateUserStatus(
        @Bind("user_locker") user: Int,
        @Bind("user_status") status: String
    )

    @SqlUpdate(
        """
        DELETE FROM user_status WHERE user_locker = :user_locker
        """
    )
    @GetGeneratedKeys
    fun deleteUserStatus(
        @Bind("user_locker") user: Int
    ):Int
}

