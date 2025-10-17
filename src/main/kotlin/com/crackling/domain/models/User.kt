package com.crackling.domain.models

import com.crackling.infrastructure.database.entities.UserEntity

class User(
    var username: String,
    val email: String,
    var password: String
) {
    companion object {
        fun fromEntity(entity: UserEntity): User {
            return User(entity.username, entity.email.value, entity.password)
        }
    }
}