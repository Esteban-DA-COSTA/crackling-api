package com.crackling.databases.dtos

import kotlinx.serialization.Serializable

@Serializable
data class TeamDTO(val id: Int, val name: String, val description: String)
