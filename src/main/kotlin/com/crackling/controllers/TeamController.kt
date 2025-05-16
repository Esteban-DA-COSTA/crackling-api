package com.crackling.controllers

import com.crackling.databases.dtos.ListTeamDTO
import com.crackling.databases.dtos.TeamDTO
import com.crackling.databases.entities.TeamEntity
import com.crackling.databases.tables.Teams
import com.crackling.resources.HateoasLink
import com.crackling.resources.HttpVerb.*
import com.crackling.resources.MemberRessource
import com.crackling.resources.TeamRessource
import io.ktor.server.application.*
import io.ktor.server.resources.*
import org.jetbrains.exposed.sql.transactions.transaction

class TeamController(private val application: Application) {
    //#region GET
    /**
     * Retrieves all teams from the database.
     *
     * @return a list of TeamDTO representing all teams.
     */
    fun getAllTeams(): ListTeamDTO = transaction {
        ListTeamDTO(
            TeamEntity.all().map {
                it.toDTO().apply {
                    _links.putAll(
                        mapOf(
                            "self" to HateoasLink(GET, application.href(TeamRessource.Id(id = it.id.value))),
                            "edit" to HateoasLink(PUT, application.href(TeamRessource.Id(id = it.id.value))),
                            "delete" to HateoasLink(DELETE, application.href(TeamRessource.Id(id = it.id.value)))
                        )
                    )
                }
            }
        ).apply {
            _links.putAll(
                mapOf(
                    "self" to HateoasLink(GET, application.href(TeamRessource())),
                    "create" to HateoasLink(POST, application.href(TeamRessource()))
                )
            )
        }
    }

    /**
     * Retrieves a team from the database by its name.
     *
     * @param name The name of the team to retrieve.
     * @return A TeamDTO representing the team found by name.
     */
    fun getTeamByName(name: String) = transaction {
        TeamEntity.find { Teams.name eq name }.map { it.toDTO() }[0].apply {
            _links.putAll(
                mapOf(
                    "self" to HateoasLink(GET, application.href(TeamRessource.Id(id = this.id!!))),
                    "edit" to HateoasLink(PUT, application.href(TeamRessource.Id(id = this.id))),
                    "delete" to HateoasLink(DELETE, application.href(TeamRessource.Id(id = this.id)))
                )
            )
        }
    }

    /**
     * Retrieves a team from the database by its ID.
     *
     * @param id The ID of the team to retrieve.
     * @return A TeamDTO representing the team found by ID.
     */
    fun getTeamById(id: Int) = transaction {
        try {
            // teamResource used for links
            val teamResource = TeamRessource.Id(TeamRessource(), id)
            TeamEntity[id].toDTO(withMembers = true).apply {
                _links.putAll(
                    mapOf(
                        "self" to HateoasLink(GET, application.href(TeamRessource.Id(id = this.id!!))),
                        "edit" to HateoasLink(PUT, application.href(TeamRessource.Id(id = this.id))),
                        "delete" to HateoasLink(DELETE, application.href(TeamRessource.Id(id = this.id)))
                    )
                )
                // Add links to each member
                members?.forEach { member ->
                    val memberResource =
                        MemberRessource.Id(MemberRessource(teamResource), member.email)
                    member._links.putAll(
                        mapOf(
                            "self" to HateoasLink(GET, application.href(memberResource)),
                        )
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    //#endregion

    //#region CREATE
    /**
     * Creates a new team in the database.
     *
     * @param team The TeamDTO object containing the details of the team to create.
     */
    fun createTeam(team: TeamDTO) = transaction {
        val id = TeamEntity.new {
            this.name = team.name
            this.description = team.description
        }.id.value
        team.apply {
            _links.putAll(
                mapOf(
                    "self" to HateoasLink(GET, application.href(TeamRessource.Id(id = id))),
                    "edit" to HateoasLink(PUT, application.href(TeamRessource.Id(id = id))),
                    "delete" to HateoasLink(DELETE, application.href(TeamRessource.Id(id = id)))
                )
            )
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
        team.apply {
            _links.putAll(
                mapOf(
                    "self" to HateoasLink(GET, application.href(TeamRessource.Id(id = id))),
                    "edit" to HateoasLink(PUT, application.href(TeamRessource.Id(id = id)))
                )
            )
        }
    }
    //#endregion

    fun deleteTeam(id: Int) = transaction {
        TeamEntity[id].delete()
    }

}