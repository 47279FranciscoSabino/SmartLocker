package project.smartlocker.repository

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository
import project.smartlocker.domain.locker.LockerStatus
import project.smartlocker.http.models.locker.output.LockerDTO

@Repository
interface LockerRepository {
    // admin
    @SqlQuery(
        """
        SELECT 
        l.*,
        ls.locker_status
        FROM locker l 
        INNER JOIN locker_status ls ON ls.locker = l.locker_id
        """
    )
    fun getAllLockers(): List<LockerDTO>

    @SqlQuery(
        """
        SELECT * FROM locker_status
        """
    )
    fun getLockerStatus(): List<LockerStatus>

    @SqlQuery(
        """
        SELECT 
        l.*,
        ls.locker_status
        FROM locker l 
        INNER JOIN locker_status ls ON ls.locker = l.locker_id 
        WHERE l.locker_id = :id
        """
    )
    fun getLockerById(
        @Bind("id") id: Int
    ): LockerDTO?

    @SqlQuery(
        """
        SELECT 
        l.*,
        ls.locker_status
        FROM locker l
        INNER JOIN locker_status ls ON ls.locker = l.locker_id
        WHERE l.locker_hash = :hash
        """
    )
    fun getLockerByHash(
        @Bind("hash") hash: String
    ): LockerDTO?

    @SqlUpdate(
        """
        DELETE FROM locker WHERE locker_id = :id   
        """
    )
    fun deleteLocker(
        @Bind("id") id: Int
    )

    @SqlUpdate(
        """
        UPDATE locker
        SET locker_module = :module,
            locker_hash = :hash,
            locker_active = :active
        WHERE locker_id = :id
        """
    )
    @GetGeneratedKeys
    fun updateLocker(
        @Bind("id") id: Int,
        @Bind("module") module: Int,
        @Bind("hash") hash: String,
        @Bind("active") active: Boolean
    ):Int

    @SqlUpdate(
        """
        UPDATE locker_status SET locker_status = :status WHERE locker = :id
        """
    )
    fun updateLockerStatus(
        @Bind("id") id: Int,
        @Bind("status") status: String
    )

    @SqlUpdate(
        """
        INSERT INTO locker (locker_module, locker_hash, locker_active)
        VALUES (:module, :hash, :active)
        """
    )
    @GetGeneratedKeys
    fun createLocker(
        @Bind("module") module: Int,
        @Bind("hash") hash: String,
        @Bind("active") active: Boolean
    ):Int

    // global
    @SqlUpdate(
        """
        UPDATE locker
        SET locker_active = :active
        WHERE locker_id = :id
        """
    )
    @GetGeneratedKeys
    fun updateLockerInfo(
        @Bind("id") id: Int,
        @Bind("active") active: Boolean
    ):Int
}