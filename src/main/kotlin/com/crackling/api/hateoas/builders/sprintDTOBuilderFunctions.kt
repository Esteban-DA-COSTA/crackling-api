package com.crackling.api.hateoas.builders

import com.crackling.domain.models.sprint.ListSprintDTO
import com.crackling.domain.models.sprint.SprintDTO
import com.crackling.infrastructure.database.entities.SprintEntity


fun buildSprintList(init: ListSprintDTO.() -> Unit): ListSprintDTO = ListSprintDTO().apply(init)

fun ListSprintDTO.sprint(sprintEntity: SprintEntity, init: SprintDTO.() -> Unit) = SprintDTO().apply(init)