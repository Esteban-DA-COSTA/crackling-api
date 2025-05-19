package com.crackling.resources

import io.ktor.resources.*

@Resource("tasks")
class TaskResource(val parent: TeamResource.Id) {
    
    @Resource("add")
    class Add(val parent: TaskResource)
    
    @Resource("{id}")
    class Id(val parent: TaskResource, val id: Int)
    {
        @Resource("remove")
        class Remove(val parent: Id)
    }
}