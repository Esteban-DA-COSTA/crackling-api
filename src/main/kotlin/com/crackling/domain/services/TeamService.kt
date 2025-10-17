package com.crackling.domain.services


import com.crackling.domain.models.Team
import com.crackling.infrastructure.database.entities.MemberEntity
import com.crackling.infrastructure.database.entities.TeamEntity
import com.crackling.infrastructure.database.tables.Members
import com.crackling.infrastructure.database.tables.Teams
import com.crackling.infrastructure.exceptions.ResourceNotFoundException
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class TeamService() {
    //#region GET
    /**
     * Retrieves all teams from the database.
     * Just basic information will be sent to client. to get more information, use [getTeamById].
     *
     * @return a list of TeamDTO representing all teams.
     */
    fun getAllTeams(): List<Team> = transaction {
        val entities = TeamEntity.all().toList()
        buildList {
            entities.forEach { entity ->
                this.add(Team.fromEntity(entity))
            }
        }
    }


    /**
     * Retrieves a team from the database by its name.
     *
     * @param name The name of the team to retrieve.
     * @return A TeamDTO representing the team found by name.
     */
    fun getTeamByName(name: String): Team = transaction {
        val entity = TeamEntity.find { Teams.name eq name }.firstOrNull() ?: throw ResourceNotFoundException(name)
        Team.fromEntity(entity) {
            includeMembers = true
        }
    }

    fun getTeamById(id: Int): Team = transaction {
        val entity = TeamEntity.findById(id) ?: throw ResourceNotFoundException(id.toString())
        Team.fromEntity(entity) {
            includeMembers = true
        }
    }
    //#endregion

    //#region CREATE
    /**
     * Creates a new team in the database.
     *
     * @param team The TeamDTO object containing the details of the team to create.
     */
    fun createTeam(team: Team, userEmail: String): Team = transaction {
        val entity = TeamEntity.new {
            this.name = team.name
            this.description = team.description
        }
        val memberId = CompositeID {
            it[Members.user] = userEmail
            it[Members.team] = entity.id
        }
        MemberEntity.new(memberId) {
            role = "owner"
        }
        Team.fromEntity(entity)
    }
    //#endregion

    //#region Modify
    /**
     * Updates an existing team in the database.
     *
     * @param id The ID of the team to update.
     * @param team The TeamDTO object containing the updated details of the team.
     */
    fun updateTeam(id: Int, team: Team): Team {
        return transaction {
            val entity = TeamEntity.findByIdAndUpdate(id) {
                it.name = team.name
                it.description = team.description
            } ?: throw ResourceNotFoundException(id.toString())
            Team.fromEntity(entity)
        }
    }
    //#endregion

    fun deleteTeam(id: Int) = transaction {
        val entity = TeamEntity.findById(id) ?: throw ResourceNotFoundException(id.toString())
        entity.delete()
    }

}