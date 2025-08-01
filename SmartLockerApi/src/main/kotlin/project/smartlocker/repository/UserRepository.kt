package project.smartlocker.repository

import project.smartlocker.http.models.user.output.UserInfoDTO
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository
import project.smartlocker.domain.user.User
import project.smartlocker.http.models.user.output.AdminUserDTO
import project.smartlocker.http.models.user.output.AdminUserStatusDTO
import project.smartlocker.http.models.user.output.UserDTO
import java.util.UUID


@Repository
interface UserRepository {
    //admin
    @SqlQuery(
        """
        SELECT 
        u.*,
        us.user_status
        FROM user_locker u
        INNER JOIN user_status us ON us.user_locker = u.user_id
        """
    )
    fun getAllUsers(): List<AdminUserDTO>


    @SqlQuery(
        """
        SELECT * FROM user_status
        """
    )
    fun getAllUsersStatus(): List<AdminUserStatusDTO>

    @SqlUpdate(
        """
        DELETE FROM user_locker WHERE user_id = :user_id
        """
    )
    @GetGeneratedKeys
    fun deleteUser(
        @Bind("user_id") id: Int
    ):Int

    @SqlUpdate(
        """
        UPDATE user_status 
        SET user_role = :user_role
        WHERE user_locker = :user_id
        """
    )
    @GetGeneratedKeys
    fun editRole(
        @Bind("user_id") id: Int,
        @Bind("user_role") role: String
    ):Int

    //global
    @SqlQuery(
        """
        SELECT 
        u.*,
        us.user_status,
        us.user_role
        FROM user_locker u
        INNER JOIN user_status us ON us.user_locker = u.user_id
        WHERE u.user_id = :user_locker
        """
    )
    fun getUserById(
        @Bind("user_locker") user_locker: Int
    ): UserDTO?

    @SqlQuery(
        """
        SELECT * FROM user_locker u
        INNER JOIN user_status us ON user_locker = u.user_id
        WHERE user_name LIKE '%' || :username || '%'
        """
    )
    fun getUserByUsername(
        @Bind("username") username: String
    ): UserDTO?

    @SqlUpdate(
        """
        UPDATE user_locker 
        SET user_password = :user_password,
            user_email = :user_email
        WHERE user_id = :user_id
        """
    )
    @GetGeneratedKeys
    fun updateUser(
        @Bind("user_id") id: Int,
        @Bind("user_email") email: String,
        @Bind("user_password") password: String
    ):Int

    //
    @SqlQuery(
        """
        SELECT * FROM user_locker WHERE user_id = :id 
        """
    )
    fun getUser(
        @Bind("id") id: Int
    ): User?

    @SqlUpdate(
        """
        UPDATE user_status 
        SET user_status = :user_status
        WHERE user_locker = :user_id
        """
    )
    @GetGeneratedKeys
    fun editUserStatus(
        @Bind("user_id") id: Int,
        @Bind("user_status") status: String
    ):Int


// ------------------------------------------------------------------------------------
    //login
    @SqlQuery(
        """
        SELECT * FROM user_locker 
        WHERE user_email = :email
        """
    )
    fun getUserByEmail(
        @Bind("email") email: String
    ): User?


    @SqlUpdate(
        """
        UPDATE user_locker
        SET user_token = :token
        WHERE user_id = :userId    
        """
    )
    fun updateUserToken(@Bind("userId") userId: Int, @Bind("token") token: UUID)


    @SqlQuery(
        """
        SELECT * 
        FROM user_locker u
        INNER JOIN user_status us ON user_locker = u.user_id
        WHERE user_token = :token
        """
    )
    fun getUserByToken(@Bind("token") token: UUID): UserDTO?

    //logout
    @SqlUpdate(
        """
        UPDATE user_locker 
        SET user_token = NULL 
        WHERE user_id = :userId
        """
    )
    fun deleteToken(@Bind("userId") userId: Int)

    //register
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

    @SqlQuery(
        """
        SELECT * FROM user_locker WHERE user_id = :user_locker
        """
    )
    fun getUserInfo(
        @Bind("user_locker") user_locker: Int
    ): UserInfoDTO?
}