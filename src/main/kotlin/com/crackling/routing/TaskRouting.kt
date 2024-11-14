package com.crackling.routing

import com.crackling.controllers.TaskController
import com.crackling.resources.TaskResource
import com.crackling.routing.payloads.TaskAddPayload
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.resources.post as postR

fun Route.configureTaskRouting() {
    val taskController = TaskController(this.application)
    get<TaskResource> {
        return@get call.respond(taskController.getAllTasksOfTeam(it))
    }
    postR<TaskResource.Add> {
        val taskToAdd = call.receive<TaskAddPayload>()
        taskController.createTaskForTeam(it, taskToAdd)
        return@postR call.respond(HttpStatusCode.Created)
    }
}