package com.crackling.controllers

import com.crackling.databases.dtos.TeamDTO
import com.crackling.databases.entities.TeamEntity
import com.crackling.databases.tables.Teams
import org.jetbrains.exposed.sql.transactions.transaction

class TeamController {
    //#region GET
    /**
     * Retrieves all teams from the database.
     *
     * @return a list of TeamDTO representing all teams.
     */
    fun getAllTeams(): List<TeamDTO> = transaction { TeamEntity.all().map { it.toDTO() } }

    /**
     * Retrieves a team from the database by its name.
     *
     * @param name The name of the team to retrieve.
     * @return A TeamDTO representing the team found by name.
     */
    fun getTeamByName(name: String) = transaction { TeamEntity.find { Teams.name eq name }.map { it.toDTO() } }[0]

    /**
     * Retrieves a team from the database by its ID.
     *
     * @param id The ID of the team to retrieve.
     * @return A TeamDTO representing the team found by ID.
     */
    fun getTeamById(id: Int) = transaction { TeamEntity[id].toDTO() }
    //#endregion

    //#region CREATE
    /**
     * Creates a new team in the database.
     *
     * @param team The TeamDTO object containing the details of the team to create.
     */
    fun createTeam(team: TeamDTO) = transaction {
        TeamEntity.new {
            this.name = team.name
            this.description = team.description
        }
    }
    //#endregion

    //#region Modify
    /**
     * Updates an existing team in the database.
     *
     * @param id The ID of the team to update.
     * @param team The TeamDTO object containing the updated details of the team.
     */
    fun updateTeam(id: Int, team: TeamDTO) = transaction {
        TeamEntity.findByIdAndUpdate(id) {
            it.name = team.name
            it.description = team.description
        }
    }

}