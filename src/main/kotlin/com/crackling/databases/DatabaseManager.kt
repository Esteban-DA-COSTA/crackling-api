package com.crackling.databases

import com.crackling.databases.tables.Members
import com.crackling.databases.tables.Tasks
import com.crackling.databases.tables.Teams
import com.crackling.databases.tables.Users
import io.ktor.server.config.*
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object DatabaseManager {
    private lateinit var db: Database
    
    fun connection(config: ApplicationConfig) {
        db = Database.connect(
            url = config.property("database.url").getString(),
            user = config.property("database.username").getString(),
            password = config.property("database.password").getString(),
            driver = config.property("database.driver").getString(),
        )
    }
    
    fun createTables() {
        transaction {
            SchemaUtils.create(Teams, Users, Members, Tasks)
        }
    }
}