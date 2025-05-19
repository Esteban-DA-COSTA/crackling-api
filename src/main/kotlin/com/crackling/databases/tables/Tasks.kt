package com.crackling.databases.tables

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object Tasks: IntIdTable() {
    val name = varchar("name", 255)
    val description = varchar("description", 255)
    val completed = bool("completed")
    val team = reference("team", Teams)
    val userPoints = integer("userPoints")
    val assignee = optReference("assignee", Users)
    
}