package com.crackling.controllers

import com.crackling.databases.dtos.ListTeamDTO
import com.crackling.databases.dtos.TeamDTO
import com.crackling.databases.entities.TeamEntity
import com.crackling.databases.tables.Teams
import com.crackling.resources.HateoasLink
import com.crackling.resources.HttpVerb.*
import com.crackling.resources.MemberResource
import com.crackling.resources.TeamResource
import io.ktor.server.application.*
import io.ktor.server.resources.*
import org.jetbrains.exposed.sql.transactions.transaction

class TeamController(private val application: Application) {
    //#region GET
    /**
     * Retrieves all teams from the database.
     * Just basic information will be sent to client. to get more information, use [getTeamById].
     *
     * @return a list of TeamDTO representing all teams.
     */
    fun getAllTeams(): ListTeamDTO = transaction {
        ListTeamDTO(
            TeamEntity.all().map {
                it.toDTO().apply {
                    addLinks(
                        "self" to HateoasLink(GET, application.href(TeamResource.Id(id = it.id.value))),
                        "edit" to HateoasLink(PUT, application.href(TeamResource.Id(id = it.id.value))),
                        "delete" to HateoasLink(DELETE, application.href(TeamResource.Id(id = it.id.value)))
                    )
                }
            }
        ).apply {
            addLinks(
                "self" to HateoasLink(GET, application.href(TeamResource())),
                "create" to HateoasLink(POST, application.href(TeamResource()))
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
            addLinks(
                "self" to HateoasLink(GET, application.href(TeamResource.Id(id = this.id!!))),
                "edit" to HateoasLink(PUT, application.href(TeamResource.Id(id = this.id))),
                "delete" to HateoasLink(DELETE, application.href(TeamResource.Id(id = this.id)))
            )
        }
    }

    /**
     * Retrieves a team from the database by its ID.
     * When searching for a specific team, linked information as members and tasks will be sent.
     *
     * @param id The ID of the team to retrieve.
     * @return A TeamDTO representing the team found by ID.
     */
    fun getTeamById(id: Int) = transaction {
        try {
            // teamResource used for links
            val teamResource = TeamResource.Id(id = id)
            TeamEntity[id].toDTO(withMembers = true).apply {
                addLinks(
                    "self" to HateoasLink(GET, application.href(teamResource)),
                    "edit" to HateoasLink(PUT, application.href(teamResource)),
                    "delete" to HateoasLink(DELETE, application.href(teamResource)),
                )
                // Add links to each member
                members?.forEach { member ->
                    val memberResource =
                        MemberResource.Id(MemberResource(teamResource), member.email)
                    member.addLinks(
                        "self" to HateoasLink(GET, application.href(memberResource)),
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
            addLinks(
                "self" to HateoasLink(GET, application.href(TeamResource.Id(id = id))),
                "edit" to HateoasLink(PUT, application.href(TeamResource.Id(id = id))),
                "delete" to HateoasLink(DELETE, application.href(TeamResource.Id(id = id)))
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
            addLinks(
                "self" to HateoasLink(GET, application.href(TeamResource.Id(id = id))),
                "edit" to HateoasLink(PUT, application.href(TeamResource.Id(id = id)))
            )
        }
    }
    //#endregion

    fun deleteTeam(id: Int) = transaction {
        TeamEntity[id].delete()
    }

}