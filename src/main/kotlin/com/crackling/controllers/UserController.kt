package com.crackling.controllers

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.crackling.databases.dtos.UserDTO
import com.crackling.databases.dtos.UserLoggedDTO
import com.crackling.databases.entities.UserEntity
import com.crackling.databases.tables.Users
import com.crackling.exceptions.InvalidFormatException
import com.crackling.plugins.JwtInfo
import com.crackling.resources.HttpVerb.GET
import com.crackling.resources.TeamResource
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.resources.*
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class UserController(private val app: Application) {

    fun createUser(user: UserDTO) {
        if (!validateEmail(user.email)) {
            throw InvalidFormatException()
        }
        transaction {
            UserEntity.new(user.email) {
                username = user.username
                password = user.password
            }
        }
    }

    /**
     * Verifies user credentials and returns a UserLoggedDTO containing a JWT token.
     *
     * @param email The email of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @param jwtInfo An object containing JWT configuration details such as issuer and secret.
     * @return A [UserLoggedDTO] containing the generated JWT token if the credentials are valid.
     * @throws NotFoundException If the user with specified email and password is not found.
     */
    fun checkUser(email: String, password: String, jwtInfo: JwtInfo): UserLoggedDTO {
        return transaction {
            val user = UserEntity.find { (Users.id eq email) and (Users.password eq password) }.firstOrNull()
            if (user != null) {
                return@transaction buildTokenDto(user, jwtInfo)
            }
            throw NotFoundException("User not found")
        }
    }

    /**
     * Validates the format of an email address.
     *
     * @param email The email address to be validated.
     * @return True if the email address is in a valid format, false otherwise.
     */
    private fun validateEmail(email: String): Boolean {
        val beforeArobase = "(?=[a-zA-Z0-9])[a-zA-Z0-9_.-]+"
        val afterArobase = "[a-zA-Z]+\\..+"
        val regex = "^$beforeArobase@$afterArobase\$".toRegex()
        val match = regex.matches(email)
        return match
    }

    /**
     * Builds a Dto to return after a successful login.
     *
     * @param user The user for whom the token is being generated.
     * @param jwtInfo An object containing JWT configuration details such as issuer, audience, and secret.
     * @return A [UserLoggedDTO] containing the generated JWT token and associated links.
     */
    private fun buildTokenDto(user: UserEntity, jwtInfo: JwtInfo): UserLoggedDTO {
        val token = JWT.create()
            .withAudience(jwtInfo.audience)
            .withIssuer(jwtInfo.issuer)
            .sign(Algorithm.HMAC256(jwtInfo.secret))
        return UserLoggedDTO(token).apply { 
            addLinks(
                "home" to (GET on app.href(TeamResource())),
                "team" to (GET on app.href(TeamResource()))
            )
        }
    }
}