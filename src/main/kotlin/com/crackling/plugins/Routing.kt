package com.crackling.plugins

import com.crackling.routing.configureAuthRouting
import com.crackling.routing.configureMemberRouting
import com.crackling.routing.configureTaskRouting
import com.crackling.routing.configureTeamRouting
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(Resources)
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        swaggerUI("/docs", "crackling_api_spec.yaml")
        get("/helloWorld") {
            call.respondText("Hello World!")
        }
        configureAuthRouting()
        authenticate {
            configureTeamRouting()
            configureMemberRouting()
            configureTaskRouting()
        }
    }
}
