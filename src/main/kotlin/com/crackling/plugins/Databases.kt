package com.crackling.plugins

import com.crackling.databases.DatabaseManager
import io.ktor.server.application.*

fun Application.configureDatabases() {
    DatabaseManager.createTables()
}