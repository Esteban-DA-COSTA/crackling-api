package com.crackling.databases.entities

import com.crackling.databases.dtos.TaskDTO
import com.crackling.databases.dtos.UserDTO
import com.crackling.databases.tables.Tasks
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class TaskEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TaskEntity>(Tasks)

    var name by Tasks.name
    var description by Tasks.description
    var completed by Tasks.completed
    var team by TeamEntity referencedOn Tasks.team
    var userPoints by Tasks.userPoints
    var assignee by UserEntity optionalReferencedOn Tasks.assignee

    fun toDTO(addTeam: Boolean = false, addMember: Boolean = false) = 
        TaskDTO(
            name,
            description,
            completed,
            if (addTeam) team.toDTO() else null,
            (if (addMember) assignee?.toDTO() else null) as UserDTO?
        )
    
    operator fun invoke() = toDTO()
    
    operator fun plusAssign(points: Int) {
        this.userPoints += points
    }
}