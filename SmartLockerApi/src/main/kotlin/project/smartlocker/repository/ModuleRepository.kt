package project.smartlocker.repository

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository
import project.smartlocker.domain.module.ModuleStatus
import project.smartlocker.http.models.module.output.AdminModuleDTO
import project.smartlocker.http.models.module.output.ModuleDTO

@Repository
interface ModuleRepository {
    // admin
    @SqlQuery(
        """
        SELECT 
        m.*,
        ms.module_status 
        FROM module m 
        INNER JOIN module_status ms ON ms.module = m.module_id
        """
    )
    fun getAllModules(): List<AdminModuleDTO>

    @SqlQuery(
        """
        SELECT * FROM module_status
        """
    )
    fun getAllModulesStatus(): List<ModuleStatus>

    @SqlQuery(
        """
        SELECT 
        m.*,
        ms.module_status 
        FROM module m 
        INNER JOIN module_status ms ON ms.module = m.module_id
        WHERE m.module_id = :module_id 
        """
    )
    fun getModuleById(
        @Bind("module_id") id: Int
    ): AdminModuleDTO?

    @SqlUpdate(
        """
        INSERT INTO module (module_location, module_location_name, module_n)
        VALUES (ST_GeogFromText(:module_location),:module_location_name, :module_n)
        """
    )
    @GetGeneratedKeys
    fun createModule(
        @Bind("module_location") location: String,
        @Bind("module_location_name") locname: String,
        @Bind("module_n") maxN: Int
    ):Int

    @SqlUpdate(
        """
        UPDATE module 
        SET module_location = ST_GeogFromText( :module_location),
            module_location_name = :module_location_name,
            module_n = :module_n
        WHERE module_id = :module_id
        """
    )
    @GetGeneratedKeys
    fun updateModule(
        @Bind("module_id") id: Int,
        @Bind("module_location") location: String,
        @Bind("module_location_name") locname: String,
        @Bind("module_n") maxN: Int
    ):Int

    @SqlUpdate(
        """
        UPDATE module_status
        SET module_status = :module_status
        WHERE module = :module
        """
    )
    fun updateModuleStatus(
        @Bind("module") module: Int,
        @Bind("module_status") status: String
    )

    @SqlUpdate(
        """
        DELETE FROM module WHERE module_id = :module_id
        """
    )
    fun deleteModule(
        @Bind("module_id") id: Int
    )

    // global
    @SqlQuery(
        """
        SELECT 
            m.*,
            ms.module_status,
            COUNT(ls.*) FILTER (WHERE ls.locker_status = 'AVAILABLE') AS available_lockers
        FROM module m 
        INNER JOIN module_status ms ON ms.module = m.module_id
        INNER JOIN locker l ON l.locker_module = m.module_id
        INNER JOIN locker_status ls ON ls.locker = l.locker_id
        WHERE l.locker_active = TRUE
        AND ST_DWithin(
            m.module_location::geography,
            ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography,
            :radius
        )
        GROUP BY 
            m.module_id, m.module_location, m.module_n, m.module_location_name, ms.module_status;
        """
    )
    fun getModulesByRadius(
        @Bind("longitude") longitude: Double,
        @Bind("latitude") latitude: Double,
        @Bind("radius") radius: Double
    ): List<ModuleDTO>

}