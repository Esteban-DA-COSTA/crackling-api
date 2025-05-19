package com.crackling.resources

import io.ktor.resources.*

@Resource("tasks")
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
typealias TaskRemovalResource = TaskResource.Id.Remove
//#endregion