package com.crackling.infrastructure.database

import com.crackling.infrastructure.database.tables.Members
import com.crackling.infrastructure.database.tables.Tasks
import com.crackling.infrastructure.database.tables.Teams
import com.crackling.infrastructure.database.tables.Users
import io.ktor.server.config.*
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object DatabaseManager {
    private lateinit var db: Database
    
    fun connection(config: ApplicationConfig) {
        db = if (config.property(("database.useH2")).getString().toBoolean())
            connectToH2(config)
        else
            connectToSQLServer(config)
    }
    
    fun createTables() {
        transaction {
            SchemaUtils.create(Teams, Users, Members, Tasks)
        }
    }

    private fun connectToH2(config: ApplicationConfig) = Database.connect(
        "jdbc:h2:./myh2file",
        driver = "org.h2.Driver"
    )

    private fun connectToSQLServer(config: ApplicationConfig) = Database.connect(
        url = config.property("database.url").getString(),
        user = config.property("database.username").getString(),
        password = config.property("database.password").getString(),
        driver = config.property("database.driver").getString(),
    )
}