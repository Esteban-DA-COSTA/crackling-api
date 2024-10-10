package com.crackling.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*

fun Application.configureMonitoring() {
    install(CallLogging) {
//        level = Level.INFO
//        filter { call -> call.request.path().startsWith("/") }
    }
}
