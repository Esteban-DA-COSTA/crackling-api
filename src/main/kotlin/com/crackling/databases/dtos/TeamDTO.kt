package com.crackling.databases.dtos

import com.crackling.resources.HateoasLinks
import kotlinx.serialization.Serializable

@Serializable
data class TeamDTO(
    var id: Int? = null,
    var name: String,
    var description: String,
    var members: ListMembersDTO? = null,
    var tasks: ListTaskDTO? = null,
) : HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
}

@Serializable
data class ListTeamDTO(val list: MutableList<TeamDTO> = mutableListOf()) : HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
}
