package com.crackling.databases.tables

import org.jetbrains.exposed.v1.core.dao.id.IdTable

object Users: IdTable<String>() {
    val email = varchar("email", 255).entityId()
    val username = varchar("username", 255).uniqueIndex()
    val password = varchar("password", 255)
    val biography = text("biography").nullable()
    val avatar = blob("avatar").nullable()
    val background = blob("background").nullable()
    val salt = varchar("_salt", 255)
    
    override val id = email
    override val primaryKey = PrimaryKey(email)
}