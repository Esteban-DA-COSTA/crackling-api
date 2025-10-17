package com.crackling.application.api.routing

import com.crackling.application.api.plugins.jwtInfo
import com.crackling.application.api.resources.AuthResource
import com.crackling.application.api.routing.payloads.UserLoginPayload
import com.crackling.application.api.routing.payloads.UserRegisterPayload
import com.crackling.application.mappers.buildTokenDto
import com.crackling.application.mappers.parseUser
import com.crackling.domain.services.UserService
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.application
import io.ktor.server.resources.post

context(app: Application)
fun Route.configureAuthRouting() {
    val jwtInfo = application.jwtInfo
    val userService = UserService(application)

    get<AuthResource>{
        call.respond("Hello from Auth")
    }
    post<AuthResource.Login> {
        val (email, password) = call.receive<UserLoginPayload>()
        userService.checkUser(email, password)?.let {
            call.respond(buildTokenDto(it, jwtInfo))
        }
    }
    post<AuthResource.Register> {
        val dto = call.receive<UserRegisterPayload>()
        val user = parseUser(dto)
        val createdUser = userService.createUser(user)
        return@post call.respond(buildTokenDto(createdUser, jwtInfo))
    }
}