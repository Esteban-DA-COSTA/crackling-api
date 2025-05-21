package com.crackling.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.crackling.controllers.UserController
import com.crackling.databases.dtos.UserDTO
import com.crackling.resources.AuthResource
import com.crackling.routing.payloads.UserLoginPayload
import com.crackling.routing.payloads.UserRegisterPayload
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSecurity() {
    // Read JWT configuration from application.yaml
    val jwtInfo = JwtInfo(this)
    
    authentication {
        jwt {
            realm = jwtInfo.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtInfo.secret))
                    .withAudience(jwtInfo.audience)
                    .withIssuer(jwtInfo.issuer)
                    .build()
            )
            validate { credential ->
                JWTPrincipal(credential.payload)
            }
        }
    }
    routing {
        post<AuthResource.Login> {
            val (email, password) = call.receive<UserLoginPayload>()
            val userController = UserController(application)
            return@post call.respond(userController.checkUser(email, password, jwtInfo))
        }
    }
}

fun Application.configureCORSPolicy() {
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Patch)
    }
}

class JwtInfo(application: Application) {
    val issuer = application.environment.config.property("jwt.issuer").getString()
    val audience = application.environment.config.property("jwt.audience").getString()
    val realm = application.environment.config.property("jwt.realm").getString()
    val secret = application.environment.config.propertyOrNull("jwt.secret")?.getString() ?: "secret"
}