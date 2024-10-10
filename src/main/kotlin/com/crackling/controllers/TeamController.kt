package com.crackling.controllers

import com.crackling.databases.entities.TeamEntity
import com.crackling.databases.dtos.TeamDTO
import org.jetbrains.exposed.sql.transactions.transaction

class TeamController {
    fun getAllTeams(): List<TeamDTO> = transaction { TeamEntity.all().map { it.toDTO()} }
}