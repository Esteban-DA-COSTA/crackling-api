package com.crackling.databases.entities

import com.crackling.databases.dtos.TaskDTO
import com.crackling.databases.tables.Tasks
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class TaskEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TaskEntity>(Tasks)

    var name by Tasks.name
    var description by Tasks.description
    var completed by Tasks.completed
    var team by TeamEntity referencedOn Tasks.teamId
    var userPoints by Tasks.userPoints

    fun toDTO(addTeam: Boolean = false) = TaskDTO(name, description, completed, if (addTeam) team.toDTO() else null)
}