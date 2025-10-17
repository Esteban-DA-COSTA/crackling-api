package com.crackling.infrastructure.database.entities

import com.crackling.application.dtos.task.TaskDTO
import com.crackling.infrastructure.database.tables.Tasks
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class TaskEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TaskEntity>(Tasks)

    var name by Tasks.name
    var description by Tasks.description
    var completed by Tasks.completed
    var team by TeamEntity.Companion referencedOn Tasks.team
    var userPoints by Tasks.userPoints
    var assignee by UserEntity optionalReferencedOn Tasks.assignee
    
    operator fun plusAssign(points: Int) {
        this.userPoints += points
    }
}