package com.crackling.application.api.routing

import com.crackling.application.api.resources.TeamResource
import com.crackling.application.dtos.team.TeamDTO
import com.crackling.application.mappers.buildTeamDto
import com.crackling.application.mappers.buildTeamListDto
import com.crackling.application.mappers.parseTeam
import com.crackling.domain.services.TeamService
import io.ktor.http.*
import io.ktor.server.application.Application
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.response.*
import io.ktor.server.routing.*

context(app: Application)
fun Route.configureTeamRouting() {
    val teamService = TeamService()
    get<TeamResource> {
        if (it.name != null) {
            val team = teamService.getTeamByName(it.name)
            call.respond(buildTeamDto(team))
        } else {
            val teams = teamService.getAllTeams()
            call.respond(context(call.application) { buildTeamListDto(teams) })
        }
    }
    get<TeamResource.Id> {
        val team = teamService.getTeamById(it.teamId)
        call.respond(context(call.application) { buildTeamDto(team) })
    }
    post<TeamResource> {
        val userMail = call.principal<JWTPrincipal>()!!.getClaim("email", String::class)!!
        val teamDTO = call.receive<TeamDTO>()
        val createdTeam = teamService.createTeam(parseTeam(teamDTO), userMail)
        call.respond(HttpStatusCode.Created, teamDTO)
    }
    put<TeamResource.Id> {
        val teamDTO = call.receive<TeamDTO>()
        teamService.updateTeam(it.teamId, parseTeam(teamDTO))
        call.respond(HttpStatusCode.OK, teamDTO)
    }
    delete<TeamResource.Id> {
        teamService.deleteTeam(it.teamId)
    }
}