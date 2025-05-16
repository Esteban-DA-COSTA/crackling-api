package com.crackling.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.cors.routing.CORS

fun Application.configureSecurity() {
    // Read JWT configuration from application.yaml
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtDomain = environment.config.property("jwt.domain").getString()
    val jwtRealm = environment.config.property("jwt.realm").getString()
    val jwtSecret = environment.config.propertyOrNull("jwt.secret")?.getString() ?: "secret"
    authentication {
        jwt {
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
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
