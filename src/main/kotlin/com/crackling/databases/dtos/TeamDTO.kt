package com.crackling.databases.dtos

import com.crackling.resources.HateoasLink
import com.crackling.resources.HateoasLinks
import kotlinx.serialization.Serializable

@Serializable
data class TeamDTO(val id: Int? = null, val name: String, val description: String): HatoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
}
