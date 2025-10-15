package com.crackling.application.api.hateoas.builders

import com.crackling.application.dtos.member.ListMembersDTO
import com.crackling.application.dtos.sprint.ListSprintDTO
import com.crackling.application.dtos.task.ListTaskDTO
import com.crackling.application.dtos.team.ListTeamDTO
import com.crackling.application.dtos.team.TeamDTO
import com.crackling.infrastructure.database.entities.TeamEntity

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

fun TeamDTO.sprints(init: ListSprintDTO.() -> Unit): ListSprintDTO {
    val sprints = ListSprintDTO()
    sprints.init()
    return sprints
}