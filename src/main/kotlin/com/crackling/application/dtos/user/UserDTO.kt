package com.crackling.application.dtos.user

import com.crackling.application.api.hateoas.HateoasDTO
import com.crackling.application.api.hateoas.HateoasLink
import com.crackling.application.api.hateoas.HateoasLinks
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class UserDTO(val email: String, val username: String, @Transient val password: String = ""): HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
}

@Serializable
data class UserLoggedDTO(val token: String): HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
}