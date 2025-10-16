package com.crackling.application.mappers

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.crackling.application.api.plugins.JwtInfo
import com.crackling.application.dtos.user.UserLoggedDTO
import com.crackling.domain.models.User
import io.ktor.server.resources.href
import com.crackling.application.api.resources.HttpVerb.*
import com.crackling.application.api.resources.TeamResource
import io.ktor.server.application.Application


/**
 * Builds a Dto to return after a successful login.
 *
 * @param user The user for whom the token is being generated.
 * @param jwtInfo An object containing JWT configuration details such as issuer, audience, and secret.
 * @return A [UserLoggedDTO] containing the generated JWT token and associated links.
 */
context(app: Application)
fun buildTokenDto(user: User, jwtInfo: JwtInfo): UserLoggedDTO {
    val token = JWT.create()
        .withAudience(jwtInfo.audience)
        .withIssuer(jwtInfo.issuer)
        .withClaim("email", user.email)
        .sign(Algorithm.HMAC256(jwtInfo.secret))
    return UserLoggedDTO(token).apply {
        addLinks(
            "home" to (GET on app.href(TeamResource())),
            "teams" to (GET on app.href(TeamResource()))
        )
    }
}
