package com.crackling.databases.dtos

import com.crackling.resources.HateoasLinks

data class TaskDTO(
    val name: String,
    val description: String,
    val completed: Boolean,
    val team: TeamDTO? = null,
) : HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
}
