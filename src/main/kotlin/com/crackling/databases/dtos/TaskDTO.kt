package com.crackling.databases.dtos

import com.crackling.resources.HateoasLink
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
    override fun addLinks(vararg link: Pair<String, HateoasLink>): TaskDTO {
        link.forEach { _links += it }
        return this
    }
}

@Serializable
data class ListTaskDTO(val list: List<TaskDTO> = listOf()) : HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
    override fun addLinks(vararg link: Pair<String, HateoasLink>): ListTaskDTO {
        link.forEach { _links += it }
        return this
    }
}
