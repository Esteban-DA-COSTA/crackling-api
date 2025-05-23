package com.crackling.api.hateoas.builders

import com.crackling.api.hateoas.HateoasDTO
import com.crackling.api.hateoas.HateoasLink
import com.crackling.api.resources.HttpVerb
import com.crackling.domain.entities.MemberEntity
import com.crackling.domain.entities.TaskEntity
import com.crackling.domain.entities.TeamEntity
import com.crackling.domain.models.member.ListMembersDTO
import com.crackling.domain.models.member.MemberDTO
import com.crackling.domain.models.task.ListTaskDTO
import com.crackling.domain.models.task.TaskDTO
import com.crackling.domain.models.team.ListTeamDTO
import com.crackling.domain.models.team.TeamDTO

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

fun ListTeamDTO.team(entity: TeamEntity? = null, init: (TeamDTO.() -> Unit)): TeamDTO {
    val team = entity?.toDTO() ?: TeamDTO(null, "", "")
    team.init()
    list.add(team)
    return team
}

fun TeamDTO.members(init: (ListMembersDTO.() -> Unit)): ListMembersDTO {
    members = ListMembersDTO()
    members!!.init()
    return members!!
}

fun TeamDTO.tasks(init: (ListTaskDTO.() -> Unit)): ListTaskDTO {
    tasks = ListTaskDTO()
    tasks!!.init()
    return tasks!!
}

fun ListMembersDTO.member(member: MemberEntity, init: (MemberDTO.() -> Unit)): ListMembersDTO {
    list.add(member.toDTO().also { it.init() })
    return this
}

fun ListTaskDTO.task(taskEntity: TaskEntity, init: (TaskDTO.() -> Unit)): MutableList<TaskDTO> {
    list.add(taskEntity.toDTO().also { it.init() })
    return list
}

fun HateoasDTO.action(name: String, init: HateoasLink.() -> Unit): HateoasLink {
    val link = HateoasLink(HttpVerb.GET, "")
    link.init()
    _links += name to link
    return link
}