package com.crackling.routing

import com.crackling.PokerPlanningManager
import com.crackling.databases.dtos.PokerPlanningPayload
import com.crackling.resources.TeamRessource
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.websocket.converter
import io.ktor.server.websocket.receiveDeserialized
import io.ktor.server.websocket.webSocket
import io.ktor.util.reflect.TypeInfo
import io.ktor.utils.io.charsets.Charset
import io.ktor.websocket.Frame
import io.ktor.websocket.send

fun Route.configurePokerRouting() {
    
    
    post<TeamRessource.Id.Poker.Create> {
        val room = PokerPlanningManager.createRoom(it.parent.parent.id)
        call.respond(room)
    }
    post<TeamRessource.Id.Poker.Close> {
        PokerPlanningManager.closeRoom(it.parent.parent.id)
        call.respond(HttpStatusCode.OK)
    }
    
    webSocket("teams/{id}/poker") {
        val room = call.parameters["id"]?.toIntOrNull()
        if (room == null) {
            return@webSocket call.respond(HttpStatusCode.BadRequest)
        } else {
            val payload = receiveDeserialized<PokerPlanningPayload>()
            PokerPlanningManager.joinRoom(room, payload.clientName, this)
            send("You're connected to room $room")
            for (frame in incoming) {
                when (frame) {
                    is Frame.Text -> {
                        val payload = this.converter!!.deserialize(Charset.defaultCharset(), TypeInfo(
                            PokerPlanningPayload::class), frame) as PokerPlanningPayload
                        PokerPlanningManager.sendMessageToRoom(room, payload.clientName, payload.action ?: "")
                    }

                    is Frame.Binary -> TODO()
                    is Frame.Close -> TODO()
                    is Frame.Ping -> TODO()
                    is Frame.Pong -> TODO()
                }
            }
        }
    }
}