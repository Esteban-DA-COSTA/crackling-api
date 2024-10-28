package com.crackling.controllers

import com.crackling.databases.dtos.UserDTO
import com.crackling.databases.entities.UserEntity
import com.crackling.exceptions.InvalidFormatException
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.transactions.transaction

class UserController(private val application: Application) {

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
}