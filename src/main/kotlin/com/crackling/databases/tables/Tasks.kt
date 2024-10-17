package com.crackling.databases.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Tasks: IntIdTable() {
    val name = varchar("name", 255)
    val description = varchar("description", 255)
    val completed = bool("completed")
    val teamId = reference("teamId", Teams)
}