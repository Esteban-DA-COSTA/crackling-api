package com.crackling.api.routing

import com.crackling.api.resources.TaskResource
import com.crackling.api.routing.payloads.TaskAddPayload
import com.crackling.domain.services.TaskService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.resources.post as postR

fun Route.configureTaskRouting() {
    val taskService = TaskService(this.application)
    get<TaskResource> {
        return@get call.respond(taskService.getAllTasksOfTeam(it))
    }
    postR<TaskResource.Add> {
        val taskToAdd = call.receive<TaskAddPayload>()
        taskService.createTaskForTeam(it, taskToAdd)
        return@postR call.respond(HttpStatusCode.Created)
    }
}