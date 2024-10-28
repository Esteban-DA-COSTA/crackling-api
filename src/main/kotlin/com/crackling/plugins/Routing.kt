package com.crackling.plugins

import com.crackling.routing.configurePokerRouting
import com.crackling.routing.configureTeamRouting
import io.ktor.http.*
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

fun Application.configureRouting() {
    install(Resources)
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(Json)
        pingPeriod = 30.seconds
    }
    routing {
        
        
        swaggerUI("/docs", "crackling_api_spec.yaml")
        get("/helloWorld") {
            call.respondText("Hello World!")
        }
        configureTeamRouting()
        configurePokerRouting()
    }
}
