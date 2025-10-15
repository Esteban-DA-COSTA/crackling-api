package com.crackling.application.api.routing

import com.crackling.api.resources.SprintResource
import com.crackling.domain.services.SprintService
import io.ktor.server.resources.get
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import io.ktor.server.routing.application
import io.ktor.server.routing.route

fun Route.configureSprintRouting() {
    val sprintService = SprintService(this.application)
    route("/sprints") {
        get<SprintResource> {
            val teamId = it.parent.teamId
            call.respond(sprintService.getAllSprintsOfTeam(teamId))
        }
    }

}