package com.crackling.api.plugins

import com.crackling.api.routing.configureAuthRouting
import com.crackling.api.routing.configureMemberRouting
import com.crackling.api.routing.configureTaskRouting
import com.crackling.api.routing.configureTeamRouting
import com.crackling.infrastructure.exceptions.ResourceNotFoundException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(Resources)
    install(StatusPages) {
        exception<ResourceNotFoundException> { call, cause ->
            call.respondText(status = HttpStatusCode.NotFound, text = "404: ${cause.message}")
        }
        exception<NotFoundException> { call, cause ->
            call.respondText(status = HttpStatusCode.NotFound, text = "404: ${cause.message}")
        }
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        configureAuthRouting()
        authenticate {
            configureTeamRouting()
            configureMemberRouting()
            configureTaskRouting()
        }
    }
}
