package project.smartlocker.config

import jakarta.annotation.PostConstruct
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.postgres.PostgresPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.postgis.PGgeometry
import org.postgresql.PGConnection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.stereotype.Component
import project.smartlocker.repository.*
import project.smartlocker.repository.mappers.*
//import project.smartlocker.repository.LockerRepository
import javax.sql.DataSource

@Configuration
class DatabaseConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun driverManagerDataSource(): DataSource {
        return DriverManagerDataSource()
    }

    @Bean
    fun dataSourceTransactionManager(dataSource: DataSource?): DataSourceTransactionManager {
        val dataSourceTransactionManager = DataSourceTransactionManager()
        dataSourceTransactionManager.dataSource = dataSource
        return dataSourceTransactionManager
    }

    @Bean
    fun jdbi(dataSource: DataSource?): Jdbi {
        return Jdbi.create(dataSource)
            .installPlugin(SqlObjectPlugin())
            .installPlugin(PostgresPlugin())
    }

    @Bean
    fun userRepository(jdbi: Jdbi): UserRepository {
        jdbi.registerRowMapper(UserMapper())
        jdbi.registerRowMapper(UserStatusMapper())
        return jdbi.onDemand(UserRepository::class.java)
    }

    @Bean
    fun moduleRepository(jdbi: Jdbi): ModuleRepository {
        jdbi.registerRowMapper(ModuleMapper())
        jdbi.registerRowMapper(ModuleStatusMapper())
        jdbi.registerRowMapper(ModuleAppMapper())
        return jdbi.onDemand(ModuleRepository::class.java)
    }

    @Bean
    fun lockerRepository(jdbi: Jdbi): LockerRepository {
        jdbi.registerRowMapper(LockerMapper())
        jdbi.registerRowMapper(LockerStatusMapper())
        return jdbi.onDemand(LockerRepository::class.java)
    }

    @Bean
    fun tradeRepository(jdbi: Jdbi): TradeRepository {
        jdbi.registerRowMapper(TradeMapper())
        jdbi.registerRowMapper(TradeStatusMapper())
        return jdbi.onDemand(TradeRepository::class.java)
    }

    @Bean
    fun friendsRepository(jdbi: Jdbi): FriendsRepository {
        jdbi.registerRowMapper(FriendsMapper())
        jdbi.registerRowMapper(FriendsStatusMapper())
        return jdbi.onDemand(FriendsRepository::class.java)
    }

}


@Component
class PostgisInitializer(
    private val dataSource: DataSource
) {
    @PostConstruct
    fun registerPostgisTypes() {
        val conn = dataSource.connection.unwrap(PGConnection::class.java)
        conn.addDataType("geometry", PGgeometry::class.java)
        conn.addDataType("geography", PGgeometry::class.java)
    }
}