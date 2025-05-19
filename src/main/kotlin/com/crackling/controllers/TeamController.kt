package com.crackling.controllers

import com.crackling.databases.dtos.ListTeamDTO
import com.crackling.databases.dtos.TeamDTO
import com.crackling.databases.entities.TeamEntity
import com.crackling.databases.tables.Teams
import com.crackling.resources.*
import com.crackling.resources.HttpVerb.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

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
                        "self" to HateoasLink(GET, application.href(TeamResource.Id(teamId = it.id.value))),
                        "edit" to HateoasLink(PUT, application.href(TeamResource.Id(teamId = it.id.value))),
                        "delete" to HateoasLink(DELETE, application.href(TeamResource.Id(teamId = it.id.value)))
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
                "self" to HateoasLink(GET, application.href(TeamResource.Id(teamId = this.id!!))),
                "edit" to HateoasLink(PUT, application.href(TeamResource.Id(teamId = this.id))),
                "delete" to HateoasLink(DELETE, application.href(TeamResource.Id(teamId = this.id)))
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
            val teamResource = TeamResource.Id(teamId = id)
            TeamEntity[id].toDTO(withMembers = true, withTasks = true).apply {
                addLinks(
                    "self" to HateoasLink(GET, application.href(teamResource)),
                    "edit" to HateoasLink(PUT, application.href(teamResource)),
                    "delete" to HateoasLink(DELETE, application.href(teamResource)),
                )
                // Add links to each member
                members?.list?.forEach { member ->
                    val memberResource =
                        MemberResource.Id(MemberResource(teamResource), member.email)
                    member.addLinks(
                        "self" to HateoasLink(GET, application.href(memberResource)),
                        "removeFromTeam" to HateoasLink(
                            DELETE,
                            application.href(MemberRemovalResource(memberResource))
                        ),
                        "changeRole" to HateoasLink(
                            PUT,
                            application.href(MemberRoleResource(memberResource))
                        )
                    )
                }
                members?.addLinks(
                    "self" to HateoasLink(GET, application.href(MemberResource(teamResource))),
                    "add" to HateoasLink(
                        POST,
                        application.href(MemberResource.Add(MemberResource(teamResource)))
                    )
                )
                tasks?.list?.forEach { task ->
                    val taskResource = TaskResource.Id(TaskResource(teamResource), task.id!!)
                    task.addLinks(
                        "self" to HateoasLink(GET, application.href(taskResource)),
                        "remove" to HateoasLink(
                            DELETE,
                            application.href(TaskRemovalResource(taskResource))
                        ),
                    )
                }
                tasks?.addLinks(
                    "self" to HateoasLink(GET, application.href(TaskResource(teamResource))),
                    "add" to HateoasLink(
                        POST,
                        application.href(TaskResource.Add(TaskResource(teamResource)))
                    )
                )
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
                "self" to HateoasLink(GET, application.href(TeamResource.Id(teamId = id))),
                "edit" to HateoasLink(PUT, application.href(TeamResource.Id(teamId = id))),
                "delete" to HateoasLink(DELETE, application.href(TeamResource.Id(teamId = id)))
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
                "self" to HateoasLink(GET, application.href(TeamResource.Id(teamId = id))),
                "edit" to HateoasLink(PUT, application.href(TeamResource.Id(teamId = id)))
            )
        }
    }
    //#endregion

    fun deleteTeam(id: Int) = transaction {
        TeamEntity[id].delete()
    }

}