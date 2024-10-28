package com.crackling.databases.dtos

import com.crackling.resources.HateoasLinks
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(val email: String, val username: String, val password: String): HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
}