package com.crackling.routing

import com.crackling.controllers.TeamController
import com.crackling.databases.dtos.TeamDTO
import com.crackling.resources.TeamRessource
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.configureTeamRouting() {
    val teamController = TeamController(this.application)
    get<TeamRessource> {
        if (it.name != null)
            call.respond(teamController.getTeamByName(it.name))
        else
            call.respond(teamController.getAllTeams())
    }
    get<TeamRessource.Id> {
        call.respond(teamController.getTeamById(it.id))
    }
    post<TeamRessource> {
        val teamDTO = call.receive<TeamDTO>()
        teamController.createTeam(teamDTO)
        call.respond(HttpStatusCode.Created, teamDTO)
    }
    put<TeamRessource.Id> {
        val teamDTO = call.receive<TeamDTO>()
        teamController.updateTeam(it.id, teamDTO)
        call.respond(HttpStatusCode.OK, teamDTO)
    }
    delete<TeamRessource.Id> { 
        teamController.deleteTeam(it.id)
    }
}