package com.crackling.databases.dtos

import com.crackling.resources.HateoasLink
import com.crackling.resources.HateoasLinks
import kotlinx.serialization.Serializable

@Serializable
data class TeamDTO(val id: Int? = null, val name: String, val description: String): HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
    override fun addLinks(vararg link: Pair<String, HateoasLink>): TeamDTO {
        link.forEach { _links += it }
        return this
    }
}

@Serializable
data class ListTeamDTO(val list: List<TeamDTO> = listOf()): HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
    override fun addLinks(vararg link: Pair<String, HateoasLink>): ListTeamDTO {
        link.forEach { _links += it }
        return this
    }
}
