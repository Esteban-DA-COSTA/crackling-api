package com.crackling.databases.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Teams: IntIdTable() {
    val name = varchar("name", 255)
    val description = varchar("description", 255)
}