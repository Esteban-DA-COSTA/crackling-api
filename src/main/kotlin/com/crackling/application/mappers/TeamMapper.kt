package com.crackling.application.mappers

import com.crackling.application.api.resources.*
import com.crackling.application.dtos.team.ListTeamDTO
import com.crackling.application.dtos.team.TeamDTO
import com.crackling.domain.models.Team
import io.ktor.server.application.*
import io.ktor.server.resources.*


context(app: Application)
fun buildTeamDto(team: Team, config: (TeamBuilderScope.() -> Unit) = {}): TeamDTO {
    val resource = TeamResource.Id(teamId = team.id!!)

    val configScope = TeamBuilderScope().apply(config)
    val membersDto = if (configScope.includeMembers) buildMemberListDto(team.members!!, team.id) else null
    return TeamDTO(team.id, team.name, team.description).apply {
        addAction("self") {
            protocol = HttpVerb.GET
            href = app.href(resource)
        }
        addAction("update") {
            protocol = HttpVerb.PUT
            href = app.href(resource)
        }
        addAction("delete") {
            protocol = HttpVerb.DELETE
            href = app.href(resource)
        }
        addAction("members") {
            protocol = HttpVerb.GET
            href = app.href(MemberResource(resource))
        }
        addAction("tasks") {
            protocol = HttpVerb.GET
            href = app.href(TaskResource(resource))
        }
    }
}

context(app: Application)
fun buildTeamListDto(teams: List<Team>): ListTeamDTO {
    val resource = TeamResource()
    val buildList = teams.map { buildTeamDto(it) }.toMutableList()
    val teamsDto = ListTeamDTO(buildList).apply {
        addAction("self") {
            protocol = HttpVerb.GET
            href = app.href(resource)
        }
        addAction("create") {
            protocol = HttpVerb.POST
            href = app.href(resource)
        }
    }
}

class TeamBuilderScope(var includeMembers: Boolean = false, var includeTasks: Boolean = false)