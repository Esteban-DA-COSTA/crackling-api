package com.crackling.databases.dtos

import com.crackling.databases.entities.TaskEntity
import com.crackling.resources.HateoasLinks
import kotlinx.serialization.Serializable

@Serializable
data class TaskDTO(
    val id: Int? = null,
    val name: String,
    val description: String,
    val completed: Boolean,
    val team: TeamDTO? = null,
    val assignee: UserDTO? = null
) : HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
}

@Serializable
data class ListTaskDTO(val list: MutableList<TaskDTO> = mutableListOf()) : HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
    
    fun task(taskEntity: TaskEntity, init: (TaskDTO.() -> Unit)): MutableList<TaskDTO> {
        list.add(taskEntity.toDTO().also { it.init() })
        return list
    }
}
