package com.crackling.databases.tables

import org.jetbrains.exposed.v1.core.dao.id.CompositeIdTable

object Members: CompositeIdTable() {
    val user = reference("user", Users)
    val team = reference("team", Teams)
    val role = varchar("role", 255)
    
    init {
        addIdColumn(user)
        addIdColumn(team)
    }
    
    override val primaryKey = PrimaryKey(user, team)
}