package com.crackling.domain.models

import com.crackling.infrastructure.database.entities.TeamEntity


class Team(
    val id: Int? = null,
    var name: String = "",
    var description: String = ""
) {
    constructor(entity: TeamEntity) : this(
        id = entity.id.value,
        name = entity.name,
        description = entity.description
    )
}