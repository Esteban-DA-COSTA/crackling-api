package com.crackling.databases.dtos

import com.crackling.databases.entities.TeamEntity
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

    fun members(init: (ListMembersDTO.() -> Unit)): ListMembersDTO {
        members = ListMembersDTO()
        members!!.init()
        return members!!
    }

    fun tasks(init: (ListTaskDTO.() -> Unit)): ListTaskDTO {
        tasks = ListTaskDTO()
        tasks!!.init()
        return tasks!!
    }
}

@Serializable
data class ListTeamDTO(val list: MutableList<TeamDTO> = mutableListOf()) : HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()

    fun team(entity: TeamEntity? = null, init: (TeamDTO.() -> Unit)): TeamDTO {
        val team = entity?.toDTO() ?: TeamDTO(null, "", "")
        team.init()
        list.add(team)
        return team
    }
}

fun buildTeamList(init: (ListTeamDTO.() -> Unit)): ListTeamDTO {
    val list = ListTeamDTO()
    list.init()
    return list
}

fun buildTeam(entity: TeamEntity? = null, init: (TeamDTO.() -> Unit)): TeamDTO {
    val team = entity?.toDTO() ?: TeamDTO(null, "", "")
    team.init()
    return team
}
