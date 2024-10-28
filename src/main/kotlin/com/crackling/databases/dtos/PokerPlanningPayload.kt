package com.crackling.databases.dtos

import kotlinx.serialization.Serializable

@Serializable
data class PokerPlanningPayload(val clientName: String, val taskId: Int? = null, val action: String? = null)
