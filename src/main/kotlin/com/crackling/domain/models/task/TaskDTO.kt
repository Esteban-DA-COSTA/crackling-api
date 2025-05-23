package com.crackling.domain.models.task

import com.crackling.api.hateoas.HateoasDTO
import com.crackling.api.hateoas.HateoasLinks
import com.crackling.domain.models.team.TeamDTO
import com.crackling.domain.models.user.UserDTO
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
}
