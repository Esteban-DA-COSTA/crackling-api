package com.crackling.routing

import com.crackling.controllers.TeamController
import com.crackling.databases.dtos.TeamDTO
import com.crackling.resources.TeamResource
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.configureTeamRouting() {
    val teamController = TeamController(this.application)
    get<TeamResource> {
        if (it.name != null)
            call.respond(teamController.getTeamByName(it.name))
        else
            call.respond(teamController.getAllTeams())
    }
    get<TeamResource.Id> {
        val team = teamController.getTeamById(it.teamId)
        call.respond(team)
    }
    post<TeamResource> {
        val userMail = call.principal<JWTPrincipal>()!!.getClaim("email", String::class)!!
        val teamDTO = call.receive<TeamDTO>()
        teamController.createTeam(teamDTO, userMail)
        call.respond(HttpStatusCode.Created, teamDTO)
    }
    put<TeamResource.Id> {
        val teamDTO = call.receive<TeamDTO>()
        teamController.updateTeam(it.teamId, teamDTO)
        call.respond(HttpStatusCode.OK, teamDTO)
    }
    delete<TeamResource.Id> {
        teamController.deleteTeam(it.teamId)
    }
}