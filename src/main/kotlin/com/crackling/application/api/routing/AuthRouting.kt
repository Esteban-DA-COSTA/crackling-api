package com.crackling.application.api.routing

import com.crackling.application.api.plugins.jwtInfo
import com.crackling.application.api.resources.AuthResource
import com.crackling.application.api.routing.payloads.UserLoginPayload
import com.crackling.application.api.routing.payloads.UserRegisterPayload
import com.crackling.application.mappers.buildTokenDto
import com.crackling.domain.services.UserService
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.application
import io.ktor.server.routing.post


fun Route.configureAuthRouting() {
    val jwtInfo = application.jwtInfo
    val userService = UserService(application)

    get<AuthResource>{
        call.respond("Hello from Auth")
    }
    post<AuthResource.Login> {
        val (email, password) = call.receive<UserLoginPayload>()
        if (userService.checkUser(email, password)) {
            buildTokenDto()
        }
        return@post call.respond()
    }
//    post<AuthResource.Register> {
//        val returnPayload = with(call.receive<UserRegisterPayload>()) {
//             userService.createUser(UserDTO(email, username, password), jwtInfo)
//        }
//        return@post call.respond(returnPayload)
//    }
}