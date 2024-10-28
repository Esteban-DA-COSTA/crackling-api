package com.crackling

import io.ktor.server.websocket.WebSocketServerSession
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import java.util.Collections

object PokerPlanningManager {
    val sessions = Collections.synchronizedMap<Int, PokerPlanningSession>(mutableMapOf())

    fun joinRoom(roomId: Int, clientName: String, session: WebSocketServerSession) {
        sessions[roomId]!!.getOrPut(clientName) { mutableListOf(session) }
    }

    suspend fun sendMessageToRoom(roomId: Int, clientName: String, message: String) {
        sessions[roomId]?.filterNot { it.key == clientName }?.forEach {
            it.value.forEach {
                it.send(Frame.Text(message))
            }
        }
    }

    fun createRoom(id: Int): String {
        sessions.getOrPut(id) { mutableMapOf() }
        return id.toString()
    }

    suspend fun closeRoom(id: Int) {
        sessions[id]?.forEach {
            it.value.forEach {
                it.close(CloseReason(CloseReason.Codes.NORMAL, "Room closed"))
            }
        }
        sessions.remove(id)
    }
}

typealias PokerPlanningSession = MutableMap<String, MutableList<WebSocketServerSession>>