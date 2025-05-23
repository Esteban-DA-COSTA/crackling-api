package com.crackling.services

import com.crackling.api.hateoas.HateoasLink
import com.crackling.api.resources.HttpVerb
import com.crackling.api.resources.TaskResource
import com.crackling.api.routing.payloads.TaskAddPayload
import com.crackling.domain.entities.TaskEntity
import com.crackling.domain.entities.TeamEntity
import com.crackling.domain.entities.UserEntity
import com.crackling.domain.models.task.ListTaskDTO
import com.crackling.domain.tables.Tasks.team
import io.ktor.server.application.*
import io.ktor.server.resources.*
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class TaskService(private val application: Application) {

    fun getAllTasksOfTeam(self: TaskResource): ListTaskDTO = transaction {
        val teamId = self.parent.teamId
        val list = buildList {
            TaskEntity.find { team eq teamId }.forEach {
                add(it.toDTO())
            }
        }.toMutableList()
        val listDTO = ListTaskDTO(list).addLinks(
            "self" to HateoasLink(HttpVerb.POST, application.href(self)),
            "add" to HateoasLink(HttpVerb.POST, application.href(TaskResource.Add(self)))
        ) as ListTaskDTO
        return@transaction listDTO
    }


    fun createTaskForTeam(self: TaskResource.Add, task: TaskAddPayload) = transaction {
        val teamId = self.parent.parent.teamId
        val teamEntity = TeamEntity.findById(teamId)
        var assignee: UserEntity? = null
        if (task.assignee != null) {
            assignee = UserEntity.findById(task.assignee)!!
        }
        TaskEntity.new {
            name = task.name
            description = task.description
            team = teamEntity!!
            userPoints = -1
            completed = false
            if (assignee != null) this.assignee = assignee
        }
    }
}