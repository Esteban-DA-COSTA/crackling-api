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
        get<TeamRessource> { teams ->
            teamController.getAllTeams()
            val list = listOf(
                TeamDTO(1, "A", "no real"),
                TeamDTO(2, "B", "no real")
            )
            call.respond(list)
        }
        get<TeamRessource.Id> { team ->
            when (team.id) {
                1 -> call.respond(TeamDTO(1, "A", "no real"))
                2 -> call.respond(TeamDTO(2, "B", "no real"))
                else -> call.respond(HttpStatusCode.NotFound)
            }
        }
        postR<TeamRessource> { team ->
            val teamDTO = call.receive<TeamDTO>()
            
        }
    }
}
