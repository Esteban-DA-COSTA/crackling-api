package com.crackling.api.routing

import com.crackling.api.plugins.jwtInfo
import com.crackling.api.resources.AuthResource
import com.crackling.api.routing.payloads.UserLoginPayload
import com.crackling.api.routing.payloads.UserRegisterPayload
import com.crackling.domain.models.user.UserDTO
import com.crackling.services.UserService
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import io.ktor.server.routing.application

fun Route.configureAuthRouting() {
    val jwtInfo = application.jwtInfo
    val userService = UserService(application)
    
    get<AuthResource>{
        call.respond("Hello from Auth")
    }
    post<AuthResource.Login> {
        val (email, password) = call.receive<UserLoginPayload>()
        return@post call.respond(userService.checkUser(email, password, jwtInfo))
    }
    post<AuthResource.Register> {
        val returnPayload = with(call.receive<UserRegisterPayload>()) {
             userService.createUser(UserDTO(email, username, password), jwtInfo)
        }
        return@post call.respond(returnPayload)
    }
}