package com.crackling.domain.services

import com.crackling.application.dtos.user.UserLoggedDTO
import com.crackling.domain.models.User
import com.crackling.infrastructure.database.entities.UserEntity
import com.crackling.infrastructure.exceptions.InvalidFormatException
import com.crackling.infrastructure.exceptions.ResourceNotFoundException
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.*

class UserService(private val app: Application) {


    fun createUser(user: User): User {
        if (!validateEmail(user.email)) {
            throw InvalidFormatException()
        }

        val salt = generateSalt()
        val hashedPassword = hashPassword(user.password, salt)

        val userEntity = transaction {
            UserEntity.new(user.email) {
                username = user.username
                password = hashedPassword
                this.salt = salt
            }
        }
        return User.fromEntity(userEntity)
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
    fun checkUser(email: String, password: String): User? = transaction {
        val entity = UserEntity.findById(email) ?: throw ResourceNotFoundException(email)
        val hashedPassword = hashPassword(password, entity.salt)
        if (hashedPassword == entity.password) User.fromEntity(entity) else null
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
     * Generates a cryptographically secure random salt value represented as a UTF-8 string.
     *
     * @return A string representing the random salt value.
     */
    private fun generateSalt(): String {
        return Base64.getEncoder().encodeToString(SecureRandom().generateSeed(16))
    }

    /**
     * Hashes a password using a provided salt and the SHA-256 algorithm.
     *
     * @param password The plain-text password to be hashed.
     * @param salt The salt to be combined with the password for hashing.
     * @return The hashed password as a string.
     */
    private fun hashPassword(password: String, salt: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val input = (password + salt).toByteArray()
        return Base64.getEncoder().encodeToString(messageDigest.digest(input))
    }


}