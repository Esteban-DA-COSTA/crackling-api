package com.crackling.plugins

import com.crackling.controllers.TeamController
import com.crackling.databases.dtos.TeamDTO
import com.crackling.resources.TeamRessource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.resources.post as postR
import io.ktor.server.resources.put as putR
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(Resources)
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        val teamController = TeamController()
        get("/helloWorld") {
            call.respondText("Hello World!")
        }
        get<TeamRessource> {
            if (it.name != null) 
                call.respond(teamController.getTeamByName(it.name))
            else
                call.respond(teamController.getAllTeams())
        }
        get<TeamRessource.Id> { 
            call.respond(teamController.getTeamById(it.id))
        }
        postR<TeamRessource> { 
            val teamDTO = call.receive<TeamDTO>()
            teamController.createTeam(teamDTO)
            call.respond(HttpStatusCode.Created, teamDTO)
        }
        putR<TeamRessource.Id> { 
            val teamDTO = call.receive<TeamDTO>()
            teamController.updateTeam(it.id, teamDTO)
            call.respond(HttpStatusCode.OK, teamDTO)
        }
    }
}
