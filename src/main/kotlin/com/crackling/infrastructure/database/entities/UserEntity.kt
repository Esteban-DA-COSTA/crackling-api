package com.crackling.infrastructure.database.entities

import com.crackling.domain.models.user.UserDTO
import com.crackling.infrastructure.database.tables.Members
import com.crackling.infrastructure.database.tables.Users
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.dao.EntityClass

@Suppress("unused")
class UserEntity(email: EntityID<String>): Entity<String>(email) {
    companion object : EntityClass<String, UserEntity>(Users)
    
    var email by Users.email
    var username by Users.username
    var password by Users.password
    var salt by Users.salt
    val biography by Users.biography
    val avatar by Users.avatar
    val background by Users.background
    val teams by TeamEntity via Members
    
    fun toDTO() = UserDTO(email.value, username, password)
}