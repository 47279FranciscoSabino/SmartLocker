package project.smartlocker.repository

import com.example.smartlockerandroid.data.model.hash.output.QrScanDTO
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface HashRepository {
    @SqlQuery(
        """        
        SELECT * FROM hash WHERE hash = :hash
        """
    )
    fun getHash(
        @Bind("hash") hash: String
    ): QrScanDTO?

    @SqlUpdate(
        """
        INSERT INTO hash (hash, hash_qr)
        VALUES (:hash, :hash_qr)
        """
    )
    fun insertHash(
        @Bind("hash") hash: String,
        @Bind("hash_qr") hash_qr: ByteArray
    )
}