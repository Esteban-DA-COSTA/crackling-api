package com.crackling.application.dtos.task


import com.crackling.application.api.hateoas.HateoasDTO
import com.crackling.application.api.hateoas.HateoasLinks
import com.crackling.application.dtos.team.TeamDTO
import com.crackling.application.dtos.user.UserDTO
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
