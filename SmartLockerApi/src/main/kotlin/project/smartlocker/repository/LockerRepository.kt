package project.smartlocker.repository

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository
import project.smartlocker.domain.locker.Locker
import project.smartlocker.domain.locker.LockerStatus

@Repository
interface LockerRepository {
    @SqlQuery(
        """
        SELECT * FROM locker
        """
    )
    fun getAllLockers(): List<Locker>

    @SqlQuery(
        """
        SELECT * FROM locker WHERE locker_id = :id
        """
    )
    fun getLockerById(
        @Bind("id") id: Int
    ): Locker?

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
            locker_qr = :qr,
            locker_active = :active
        WHERE locker_id = :id
        """
    )
    @GetGeneratedKeys
    fun updateLocker(
        @Bind("id") id: Int,
        @Bind("module") module: Int,
        @Bind("qr") qr: String,
        @Bind("active") active: Boolean
    ):Int

    @SqlUpdate(
        """
        INSERT INTO locker (locker_module, locker_qr, locker_active)
        VALUES (:module, :qr, :active)
        """
    )
    @GetGeneratedKeys
    fun createLocker(
        @Bind("module") module: Int,
        @Bind("qr") qr: String,
        @Bind("active") active: Boolean
    ):Int

    //Status
    @SqlQuery(
        """
        SELECT * FROM locker_status WHERE locker = :locker
        """
    )
    fun getLockerStatus(
        @Bind ("locker") locker: Int
    ): LockerStatus

    @SqlUpdate(
        """
        INSERT INTO locker_status (locker, status)
        VALUES (:locker, :status)
        """
    )
    fun createLockerStatus(
        @Bind("locker") locker: Int,
        @Bind("status") status: String,
    )

    @SqlUpdate(
        """
        UPDATE locker_status SET status = :status WHERE locker = :id
        """
    )
    fun updateLockerStatus(
        @Bind("id") id: Int,
        @Bind("status") status: String
    )

    @SqlUpdate(
        """
        DELETE FROM locker_status WHERE locker = :id
        """
    )
    @GetGeneratedKeys
    fun deleteLockerStatus(
        @Bind("id") id: Int
    ):Int
}