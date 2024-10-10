package com.crackling.controllers

import com.crackling.databases.dtos.TeamDTO
import com.crackling.databases.entities.TeamEntity
import org.jetbrains.exposed.sql.transactions.transaction

class TeamController {
    fun getAllTeams(): List<TeamDTO> = transaction { TeamEntity.all().map { it.toDTO() } }

    fun createTeam(team: TeamDTO) = transaction {
        TeamEntity.new {
            this.name = team.name
            this.description = team.description
        }
    }
    
    fun getTeamById(id: Int) = transaction { TeamEntity[id].toDTO() }
}