package com.crackling.routing.payloads

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginPayload(val email: String, val password: String)

@Serializable
data class UserRegisterPayload(val username: String, val email: String, val password: String)