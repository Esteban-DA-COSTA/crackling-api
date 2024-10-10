package com.crackling.databases

import com.crackling.databases.tables.Teams
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseManager {
    private val db = Database.connect(
        url = "jdbc:sqlserver://192.168.1.100:1433;database=crackling",
        user = "crackling_user",
        driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver",
        password = "cr@ssword",
    )
    
    fun createTables() {
        transaction {
            SchemaUtils.create(Teams)
        }
    }
}