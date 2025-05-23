package com.crackling

import com.crackling.api.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureDatabases()
    configureSecurity()
    configureRouting()
    configureCORSPolicy()
    configureSerialization()
}
