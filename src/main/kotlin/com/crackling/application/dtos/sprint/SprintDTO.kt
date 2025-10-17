package com.crackling.application.dtos.sprint


import com.crackling.application.api.hateoas.HateoasDTO
import com.crackling.application.api.hateoas.HateoasLinks
import com.crackling.application.dtos.task.TaskDTO
import com.crackling.application.dtos.team.TeamDTO
import com.crackling.domain.utils.now
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class SprintDTO(
    val id: Int? = null,
    val startDate: LocalDate = now(),
    val endDate: LocalDate = now(),
    val team: TeamDTO? = null,
    val tasks: List<TaskDTO>? = null,
    override val _links: HateoasLinks = mutableMapOf(),
): HateoasDTO

@Serializable
data class ListSprintDTO(val list: MutableList<SprintDTO> = mutableListOf()) : HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
}