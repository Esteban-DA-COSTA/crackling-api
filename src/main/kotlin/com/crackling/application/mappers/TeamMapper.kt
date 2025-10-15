package com.crackling.application.mappers

import com.crackling.application.api.hateoas.builders.*
import com.crackling.application.api.resources.*
import com.crackling.application.api.resources.HttpVerb.*
import com.crackling.application.dtos.team.ListTeamDTO
import com.crackling.application.dtos.team.TeamDTO
import com.crackling.infrastructure.database.entities.TeamEntity
import io.ktor.server.application.*
import io.ktor.server.resources.*

class TeamMapper() {

    context(app: Application)
    fun buildShallowTeam(entity: TeamEntity): TeamDTO = buildTeam(entity) {
        action("self") {
            protocol = GET
            href = app.href(TeamResource.Id(teamId = entity.id.value))
        }
        action("edit") {
            protocol = PUT
            href = app.href(TeamResource.Id(teamId = entity.id.value))
        }
        action("delete") {
            protocol = DELETE
            href = app.href(TeamResource.Id(teamId = entity.id.value))
        }
    }

    context(app: Application)
    fun buildTeamList(entities: List<TeamEntity>): ListTeamDTO = buildTeamList {
        entities.forEach { teamEntity ->
            team(teamEntity) {
                action("self") {
                    protocol = GET
                    href = app.href(TeamResource.Id(teamId = teamEntity.id.value))
                }
            }
        }
    }

    context(app: Application)
    fun buildCompleteTeam(entity: TeamEntity): TeamDTO {
        val teamResource = TeamResource.Id(teamId = entity.id.value)
        return buildTeam(entity) {
            members {
                entity.members.forEach { memberEntity ->
                    member(memberEntity) {
                        action("self") {
                            protocol = GET
                            href = app.href(
                                MemberResource.Id(
                                    MemberResource(teamResource),
                                    memberEntity.user.email.value
                                )
                            )
                        }
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
                    action("self") {
                        protocol = GET
                        href = app.href(
                            MemberResource(teamResource)
                        )
                    }
                    action("remove") {}
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
    }
}