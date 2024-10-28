package com.crackling.databases.tables

import org.jetbrains.exposed.dao.id.IdTable

object Users: IdTable<String>() {
    val email = varchar("email", 255).entityId()
    val username = varchar("username", 255)
    val password = varchar("password", 255)
    
    override val id = email
    override val primaryKey = PrimaryKey(email)
}