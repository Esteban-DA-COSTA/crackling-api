package com.crackling.application.api.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.cors.routing.*

val Application.jwtInfo: JwtInfo
    get() = JwtInfo(this)

fun Application.configureSecurity() {
    val jwtInfo = jwtInfo
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
}

fun Application.configureCORSPolicy() {
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.Authorization)
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