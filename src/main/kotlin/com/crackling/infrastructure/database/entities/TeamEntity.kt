package com.crackling.infrastructure.database.entities

import com.crackling.domain.models.team.TeamDTO
import com.crackling.infrastructure.database.tables.Members
import com.crackling.infrastructure.database.tables.Sprints
import com.crackling.infrastructure.database.tables.Tasks
import com.crackling.infrastructure.database.tables.Teams
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class TeamEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TeamEntity>(Teams)

    var name by Teams.name
    var description by Teams.description
    val members by MemberEntity referrersOn Members.team
    val tasks by TaskEntity referrersOn Tasks.team
    val sprints by SprintEntity referrersOn Sprints.team

    fun toDTO(): TeamDTO = TeamDTO(
        id.value,
        name,
        description,
    )
} 
