package com.crackling.domain.services

import com.crackling.api.hateoas.builders.buildSprintList
import com.crackling.api.hateoas.builders.sprint
import com.crackling.application.dtos.sprint.ListSprintDTO
import com.crackling.domain.utils.now
import com.crackling.infrastructure.database.entities.SprintEntity
import com.crackling.infrastructure.database.entities.TeamEntity
import com.crackling.infrastructure.database.tables.Sprints
import com.crackling.infrastructure.exceptions.ResourceNotFoundException
import io.ktor.server.application.*
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class SprintService(private val app: Application) {

    fun getAllSprintsOfTeam(id: Int): ListSprintDTO {
        val sprintEntities = SprintEntity.find { Sprints.team eq id }
        return buildSprintList {
            sprintEntities.forEach { sprintEntity ->
                sprint(sprintEntity) {}
            }
        }
    }
    
    fun createNewSprint(teamId: Int, title: String, startDate: LocalDate? = null, endDate: LocalDate? = null): SprintEntity = transaction {
        val teamEntity = TeamEntity.findById(teamId) ?: throw ResourceNotFoundException(teamId.toString())
        SprintEntity.new {
            this.team = teamEntity
            this.startDate = startDate ?: now()
            this.endDate = endDate ?: now().plus(DatePeriod(months = 1))
        }
        
    }
}