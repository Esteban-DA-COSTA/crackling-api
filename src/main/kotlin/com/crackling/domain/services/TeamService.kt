package com.crackling.domain.services

import com.crackling.api.hateoas.HateoasLink
import com.crackling.api.hateoas.builders.*
import com.crackling.api.resources.*
import com.crackling.api.resources.HttpVerb.*
import com.crackling.infrastructure.database.entities.MemberEntity
import com.crackling.infrastructure.database.entities.TeamEntity
import com.crackling.domain.models.team.ListTeamDTO
import com.crackling.domain.models.team.TeamDTO
import com.crackling.infrastructure.database.tables.Members
import com.crackling.infrastructure.database.tables.Teams
import io.ktor.server.application.*
import io.ktor.server.resources.*
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class TeamService(private val app: Application) {
    //#region GET
    /**
     * Retrieves all teams from the database.
     * Just basic information will be sent to client. to get more information, use [getTeamById].
     *
     * @return a list of TeamDTO representing all teams.
     */
    fun getAllTeams(): ListTeamDTO = transaction {
        buildTeamList {
            TeamEntity.all().forEach { teamEntity ->
                team(teamEntity) {
                    action("self") {
                        protocol = GET
                        href = app.href(TeamResource.Id(teamId = teamEntity.id.value))
                    }
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
    fun getTeamByName(name: String) = transaction {
        TeamEntity.find { Teams.name eq name }.map { it.toDTO() }[0].apply {
            addLinks(
                "self" to HateoasLink(GET, app.href(TeamResource.Id(teamId = this.id!!))),
                "edit" to HateoasLink(PUT, app.href(TeamResource.Id(teamId = this.id!!))),
                "delete" to HateoasLink(DELETE, app.href(TeamResource.Id(teamId = this.id!!)))
            )
        }
    }

    fun getTeamById(id: Int) = transaction {
        try {
            // teamResource used for links
            val teamResource = TeamResource.Id(teamId = id)
            val entity = TeamEntity[id]
            buildTeam(entity) {
                members {
                    entity.members.forEach { memberEntity ->
                        member(memberEntity) {
                            action("remove") {
                                protocol = DELETE
                                href = app.href(
                                    MemberRemovalResource(
                                        MemberResource.Id(
                                            MemberResource(teamResource),
                                            memberEntity.user.email.value
                                        )
                                    )
                                )
                            }
                            action("changeRole") {
                                protocol = PATCH
                                href = app.href(
                                    MemberRoleResource(
                                        MemberResource.Id(
                                            MemberResource(teamResource),
                                            memberEntity.user.email.value
                                        )
                                    )
                                )
                            }
                        }
                    }
                    action("add") {
                        protocol = POST
                        href = app.href(MemberResource.Add(MemberResource(teamResource)))
                    }
                }
                tasks {
                    entity.tasks.forEach { taskEntity ->
                        task(taskEntity) {
                            action("self") {
                                protocol = GET
                                href = app.href(
                                    TaskResource.Id(
                                        TaskResource(teamResource),
                                        taskEntity.id.value
                                    )
                                )
                            }
                        }

                    }
                }
                sprints {
                    entity.sprints.forEach { sprintEntity ->
                        sprint(sprintEntity) {}
                    }
                }
                action("self") {
                    protocol = GET
                    href = app.href(teamResource)
                }
                action("edit") {
                    protocol = PUT
                    href = app.href(teamResource)
                }
                action("delete") {
                    protocol = DELETE
                    href = app.href(teamResource)
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
    fun createTeam(team: TeamDTO, userEmail: String) = transaction {
        val id = TeamEntity.new {
            this.name = team.name
            this.description = team.description
        }.id.value
        val teamResource = TeamResource.Id(teamId = id)
        val memberId = CompositeID {
            it[Members.user] = userEmail
            it[Members.team] = id
        }
        MemberEntity.new(memberId) {
            role = "owner"
        }
        buildTeam(TeamEntity[id]) {
            action("self") {
                protocol = GET
                href = app.href(teamResource)
            }
            action("edit") {
                protocol = PUT
                href = app.href(teamResource)
            }
            action("delete") {
                protocol = DELETE
                href = app.href(teamResource)
            }
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
    fun updateTeam(id: Int, team: TeamDTO): TeamDTO = transaction {
        val entity = TeamEntity.findByIdAndUpdate(id) {
            it.name = team.name
            it.description = team.description
        }
        buildTeam(entity) {
            action("self") {
                protocol = GET
                href = app.href(TeamResource.Id(teamId = id))
            }
            action("edit") {
                protocol = PUT
                href = app.href(TeamResource.Id(teamId = id))
            }
            action("delete") {
                protocol = DELETE
                href = app.href(TeamResource.Id(teamId = id))
            }
        }
    }
    //#endregion

    fun deleteTeam(id: Int) = transaction {
        TeamEntity[id].delete()
    }

}