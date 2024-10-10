package com.crackling.databases

import com.crackling.databases.tables.Teams
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils

object DatabaseManager {
    private val db = Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = "",
    )
    
    fun createTables() {
        SchemaUtils.createStatements(Teams)
    }
}