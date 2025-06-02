package project.smartlocker.repository

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository
import project.smartlocker.domain.module.Module
import project.smartlocker.domain.module.ModuleStatus
import project.smartlocker.domain.user.User
import project.smartlocker.http.models.module.ModuleDTO

@Repository
interface ModuleRepository {
    @SqlQuery(
        """
        SELECT * FROM module
        """
    )
    fun getAllModules(): List<Module>

    @SqlQuery(
        """
        SELECT * FROM module WHERE module_id = :module_id 
        """
    )
    fun getModuleById(
        @Bind("module_id") id: Int
    ): Module?

    @SqlUpdate(
        """
        INSERT INTO module (module_location, module_n)
        VALUES (ST_GeogFromText(:module_location), :module_n)
        """
    )
    @GetGeneratedKeys
    fun createModule(
        @Bind("module_location") location: String,
        @Bind("module_n") maxN: Int
    ):Int

    @SqlUpdate(
        """
        UPDATE module 
        SET module_location = ST_GeogFromText( :module_location),
            module_n = :module_n
        WHERE module_id = :module_id
        """
    )
    @GetGeneratedKeys
    fun updateModule(
        @Bind("module_id") id: Int,
        @Bind("module_location") location: String,
        @Bind("module_n") maxN: Int
    ):Int

    @SqlUpdate(
        """
        DELETE FROM module WHERE module_id = :module_id
        """
    )
    fun deleteModule(
        @Bind("module_id") id: Int
    )

    @SqlQuery(
        """
        SELECT * FROM module
        WHERE ST_DWithin(
            module_location::geography,
            ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography,
            :radius
        );
        """
    )
    fun getModulesByRadius(
        @Bind("longitude") longitude: Double,
        @Bind("latitude") latitude: Double,
        @Bind("radius") radius: Double
    ): List<Module>



    // STATUS
    @SqlQuery(
        """
        SELECT * FROM module_status WHERE module = :module
        """
    )
    fun getModuleStatus(
        @Bind("module") module: Int
    ): ModuleStatus?

    @SqlUpdate(
        """
        INSERT INTO module_status (module, module_status, module_location_name)
        VALUES (:module, :module_status, :module_location_name)
        """
    )
    fun createModuleStatus(
        @Bind("module") module: Int,
        @Bind("module_status") status: String,
        @Bind("module_location_name") locName: String
    )

    @SqlUpdate(
        """
        UPDATE module_status
        SET module_status = :module_status,
            module_location_name = :module_location_name
        WHERE module = :module
        """
    )
    fun updateModuleStatus(
        @Bind("module") module: Int,
        @Bind("module_status") status: String,
        @Bind("module_location_name") locName: String
    )

    @SqlUpdate(
        """
        DELETE FROM module_status WHERE module = :module
        """
    )
    @GetGeneratedKeys
    fun deleteModuleStatus(
        @Bind("module") module: Int
    ):Int

}