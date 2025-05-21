package com.crackling.routing

import com.crackling.controllers.UserController
import com.crackling.databases.dtos.UserDTO
import com.crackling.plugins.jwtInfo
import com.crackling.resources.AuthResource
import com.crackling.routing.payloads.UserLoginPayload
import com.crackling.routing.payloads.UserRegisterPayload
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import io.ktor.server.routing.application

fun Route.configureAuthRouting() {
    val jwtInfo = application.jwtInfo
    val userController = UserController(application)
    
    get<AuthResource>{
        call.respond("Hello from Auth")
    }
    post<AuthResource.Login> {
        val (email, password) = call.receive<UserLoginPayload>()
        return@post call.respond(userController.checkUser(email, password, jwtInfo))
    }
    post<AuthResource.Register> {
        val returnPayload = with(call.receive<UserRegisterPayload>()) {
             userController.createUser(UserDTO(email, username, password), jwtInfo)
        }
        return@post call.respond(returnPayload)
    }
}