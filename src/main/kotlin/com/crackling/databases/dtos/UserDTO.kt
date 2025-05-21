package com.crackling.databases.dtos

import com.crackling.resources.HateoasLink
import com.crackling.resources.HateoasLinks
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class UserDTO(val email: String, val username: String, @Transient val password: String = ""): HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
    override fun addLinks(vararg link: Pair<String, HateoasLink>): HateoasDTO {
        _links.putAll(link)
        return this
    }
}

@Serializable
data class UserLoggedDTO(val token: String): HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
    override fun addLinks(vararg link: Pair<String, HateoasLink>): HateoasDTO {
        _links.putAll(link)
        return this
    }
}