package com.crackling.application.api.hateoas.builders

import com.crackling.application.api.hateoas.HateoasDTO
import com.crackling.application.api.hateoas.HateoasLink
import com.crackling.application.api.resources.HttpVerb
import com.crackling.application.dtos.member.ListMembersDTO
import com.crackling.application.dtos.member.MemberDTO
import com.crackling.application.dtos.task.ListTaskDTO
import com.crackling.application.dtos.task.TaskDTO
import com.crackling.application.dtos.team.ListTeamDTO
import com.crackling.application.dtos.team.TeamDTO
import com.crackling.domain.models.Team
import com.crackling.infrastructure.database.entities.MemberEntity
import com.crackling.infrastructure.database.entities.TaskEntity
import io.ktor.server.application.Application

fun buildTeamList(init: (ListTeamDTO.() -> Unit)): ListTeamDTO {
    val list = ListTeamDTO()
    list.init()
    return list
}

fun buildTeam(entity: Team? = null, init: (TeamDTO.() -> Unit)): TeamDTO {
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
