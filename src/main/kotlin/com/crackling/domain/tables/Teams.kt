package com.crackling.domain.tables

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object Teams: IntIdTable("Teams") {
    val name = varchar("name", 255)
    val description = text("description")
}