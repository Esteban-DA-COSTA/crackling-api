package com.crackling.databases.entities

import com.crackling.databases.dtos.UserDTO
import com.crackling.databases.tables.Users
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(email: EntityID<String>): Entity<String>(email) {
    companion object : EntityClass<String, UserEntity>(Users)
    
    var email by Users.email
    var username by Users.username
    var password by Users.password
    
    fun toDTO() = UserDTO(email.value, username, password)
}