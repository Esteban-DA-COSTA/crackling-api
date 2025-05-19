package com.crackling.databases.entities

import com.crackling.databases.dtos.UserDTO
import com.crackling.databases.tables.Members
import com.crackling.databases.tables.Users
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.dao.EntityClass

class UserEntity(email: EntityID<String>): Entity<String>(email) {
    companion object : EntityClass<String, UserEntity>(Users)
    
    var email by Users.email
    var username by Users.username
    var password by Users.password
    val teams by TeamEntity via Members
    
    fun toDTO() = UserDTO(email.value, username, password)
}