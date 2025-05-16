package com.crackling

import com.crackling.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureDatabases()
    configureSecurity()
    configureCORSPolicy()
    configureSerialization()
    configureRouting()
}
