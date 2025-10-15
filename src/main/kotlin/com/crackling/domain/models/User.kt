package com.crackling.domain.models

import com.crackling.infrastructure.database.entities.UserEntity

class User(
    var username: String,
    val email: String,
    var password: String
) {
    constructor(entity: UserEntity) : this(
        username = entity.username,
        email = entity.email.value,
        password = entity.password
    )
}