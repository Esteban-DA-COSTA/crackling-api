package com.crackling.domain.models.sprint

import com.crackling.api.hateoas.HateoasDTO
import com.crackling.api.hateoas.HateoasLinks
import com.crackling.domain.models.task.TaskDTO
import com.crackling.domain.models.team.TeamDTO
import java.util.Date

data class SprintDTO(
    val id: Int? = null,
    val startDate: Date,
    val endDate: Date,
    val project: TeamDTO,
    val tasks: List<TaskDTO>? = null,
    override val _links: HateoasLinks = mutableMapOf(),
): HateoasDTO

data class ListSprintDTO(val list: MutableList<SprintDTO> = mutableListOf()) : HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
}