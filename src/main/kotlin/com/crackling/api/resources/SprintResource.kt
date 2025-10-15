package com.crackling.api.resources

import io.ktor.resources.Resource

@Resource("sprints")
class SprintResource(val parent: TeamResource.Id) {
    @Resource("{sprintId}")
    class Id(val sprints: SprintResource, val sprintId: Int)

    @Resource("New")
    class New(val sprints: SprintResource)
}