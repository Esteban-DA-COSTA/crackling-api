package com.crackling.application.api.routing.payloads

import kotlinx.serialization.Serializable

@Serializable
data class TaskAddPayload(
    val name: String,
    val description: String,
    val completed: Boolean = false,
    val assignee: String? = null,
)