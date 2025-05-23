package com.crackling.api.resources

import io.ktor.resources.*

@Resource("tasks")
@Suppress("unused")
class TaskResource(val parent: TeamResource.Id) {
    
    @Resource("add")
    class Add(val parent: TaskResource)
    
    @Resource("{taskId}")
    class Id(val parent: TaskResource, val taskId: Int)
    {
        @Resource("remove")
        class Remove(val parent: Id)
    }
}

//#region ALIASES
@Suppress("unused")
typealias TaskRemovalResource = TaskResource.Id.Remove
//#endregion