package com.crackling.application.mappers

import com.crackling.application.api.resources.*
import com.crackling.application.dtos.team.ListTeamDTO
import com.crackling.application.dtos.team.TeamDTO
import com.crackling.domain.models.Team
import io.ktor.server.application.*
import io.ktor.server.resources.*


context(app: Application)
fun buildTeamDto(team: Team): TeamDTO {
    val resource = TeamResource.Id(teamId = team.id!!)
    return TeamDTO(
        team.id,
        team.name,
        team.description,
        team.members?.let { buildMemberListDto(it, team.id) },
    ).apply {
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
    return ListTeamDTO(buildList).apply {
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


fun parseTeam(dto: TeamDTO) = Team(dto.id, dto.name, dto.description)