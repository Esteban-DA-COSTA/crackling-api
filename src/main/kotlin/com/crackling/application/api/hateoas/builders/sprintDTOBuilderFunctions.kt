package com.crackling.application.api.hateoas.builders

import com.crackling.application.dtos.sprint.ListSprintDTO
import com.crackling.application.dtos.sprint.SprintDTO
import com.crackling.infrastructure.database.entities.SprintEntity


fun buildSprintList(init: ListSprintDTO.() -> Unit): ListSprintDTO = ListSprintDTO().apply(init)

fun ListSprintDTO.sprint(sprintEntity: SprintEntity, init: SprintDTO.() -> Unit) = SprintDTO().apply(init)