package com.crackling.databases.dtos

import com.crackling.resources.HateoasLink
import com.crackling.resources.HateoasLinks
import kotlinx.serialization.Serializable

@Serializable
data class TeamDTO(
    val id: Int? = null,
    val name: String,
    val description: String,
    val members: ListMembersDTO? = null,
    val tasks: ListTaskDTO? = null
) : HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
    override fun addLinks(vararg link: Pair<String, HateoasLink>): TeamDTO {
        _links.putAll(link)
        return this
    }
}

@Serializable
data class ListTeamDTO(val list: List<TeamDTO> = listOf()) : HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
    override fun addLinks(vararg link: Pair<String, HateoasLink>): ListTeamDTO {
        _links.putAll(link)
        return this
    }
}
