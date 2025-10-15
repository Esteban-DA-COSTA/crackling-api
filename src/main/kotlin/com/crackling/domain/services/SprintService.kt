package com.crackling.domain.services

import com.crackling.api.hateoas.builders.buildSprintList
import com.crackling.api.hateoas.builders.sprint
import com.crackling.domain.models.sprint.ListSprintDTO
import com.crackling.infrastructure.database.entities.SprintEntity
import com.crackling.infrastructure.database.tables.Sprints
import io.ktor.server.application.Application

class SprintService(private val app: Application) {

    fun getAllSprintsOfTeam(id: Int): ListSprintDTO {
        val sprintEntities = SprintEntity.find { Sprints.team eq id }
        return buildSprintList {
            sprintEntities.forEach { sprintEntity ->
                sprint(sprintEntity) {}
            }
        }
    }
}