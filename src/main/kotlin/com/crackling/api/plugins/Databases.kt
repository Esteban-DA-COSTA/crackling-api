package com.crackling.api.plugins

import com.crackling.infrastructure.database.DatabaseManager
import io.ktor.server.application.*

fun Application.configureDatabases() {
    DatabaseManager.connection(environment.config)
    DatabaseManager.createTables()
}