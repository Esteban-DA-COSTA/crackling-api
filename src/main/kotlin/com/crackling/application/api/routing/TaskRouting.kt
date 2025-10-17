package com.crackling.application.api.routing

//fun Route.configureTaskRouting() {
//    val taskService = TaskService(this.application)
//    get<TaskResource> {
//        return@get call.respond(taskService.getAllTasksOfTeam(it))
//    }
//    postR<TaskResource.Add> {
//        val taskToAdd = call.receive<TaskAddPayload>()
//        taskService.createTaskForTeam(it, taskToAdd)
//        return@postR call.respond(HttpStatusCode.Created)
//    }
//}