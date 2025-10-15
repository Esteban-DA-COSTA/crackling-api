package com.crackling.application.dtos.team

import com.crackling.application.api.hateoas.HateoasDTO
import com.crackling.application.api.hateoas.HateoasLinks
import com.crackling.application.dtos.member.ListMembersDTO
import com.crackling.application.dtos.task.ListTaskDTO
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
