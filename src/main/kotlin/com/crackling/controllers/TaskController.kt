package com.crackling.controllers

import com.crackling.databases.dtos.ListTaskDTO
import com.crackling.databases.entities.TaskEntity
import com.crackling.databases.entities.TeamEntity
import com.crackling.databases.entities.UserEntity
import com.crackling.databases.tables.Tasks.team
import com.crackling.resources.HateoasLink
import com.crackling.resources.HttpVerb
import com.crackling.resources.TaskResource
import com.crackling.routing.payloads.TaskAddPayload
import io.ktor.server.application.*
import io.ktor.server.resources.*
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class TaskController(private val application: Application) {

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