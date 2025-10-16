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
    fun getAllTeams(): List<Team> {
        transaction {
            TeamEntity.all().toList()
        }.also {
            return buildList {
                it.forEach { entity ->
                    this.add(Team(entity))
                }
            }
        }
    }


    /**
     * Retrieves a team from the database by its name.
     *
     * @param name The name of the team to retrieve.
     * @return A TeamDTO representing the team found by name.
     */
    fun getTeamByName(name: String): Team {
        transaction {
            TeamEntity.find { Teams.name eq name }.firstOrNull() ?: throw ResourceNotFoundException(name)
        }.also { return Team(it) }
    }

    fun getTeamById(id: Int): Team {
        transaction {
            TeamEntity.findById(id) ?: throw ResourceNotFoundException(id.toString())
        }.also { return Team(it) }
    }
    //#endregion

    //#region CREATE
    /**
     * Creates a new team in the database.
     *
     * @param team The TeamDTO object containing the details of the team to create.
     */
    fun createTeam(team: Team, userEmail: String): Team {
        transaction {
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
            entity
        }.also { return Team(it) }
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
        transaction {
            TeamEntity.findByIdAndUpdate(id) {
                it.name = team.name
                it.description = team.description
            } ?: throw ResourceNotFoundException(id.toString())
        }.also { return Team(it) }
    }
    //#endregion

    fun deleteTeam(id: Int) = transaction {
        val entity = TeamEntity.findById(id) ?: throw ResourceNotFoundException(id.toString())
        entity.delete()
    }

}