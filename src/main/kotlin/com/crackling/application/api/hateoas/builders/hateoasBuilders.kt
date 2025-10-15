package com.crackling.application.api.hateoas.builders

import com.crackling.application.dtos.member.ListMembersDTO
import com.crackling.application.dtos.member.MemberDTO
import com.crackling.application.dtos.task.ListTaskDTO
import com.crackling.application.dtos.task.TaskDTO
import com.crackling.application.dtos.team.ListTeamDTO
import com.crackling.application.dtos.team.TeamDTO
import com.crackling.infrastructure.database.entities.MemberEntity
import com.crackling.infrastructure.database.entities.TaskEntity
import com.crackling.infrastructure.database.entities.TeamEntity

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

fun ListMembersDTO.member(member: MemberEntity, init: (MemberDTO.() -> Unit)): ListMembersDTO {
    list.add(member.toDTO().also { it.init() })
    return this
}

fun ListTaskDTO.task(taskEntity: TaskEntity, init: (TaskDTO.() -> Unit)): MutableList<TaskDTO> {
    list.add(taskEntity.toDTO().also { it.init() })
    return list
}

fun com.crackling.application.api.hateoas.HateoasDTO.action(name: String, init: com.crackling.application.api.hateoas.HateoasLink.() -> Unit): com.crackling.application.api.hateoas.HateoasLink {
    val link = _root_ide_package_.com.crackling.application.api.hateoas.HateoasLink(
        _root_ide_package_.com.crackling.application.api.resources.HttpVerb.GET,
        ""
    )
    link.init()
    _links += name to link
    return link
}