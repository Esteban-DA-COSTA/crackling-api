package com.crackling.infrastructure.database.entities

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity

import com.crackling.infrastructure.database.tables.Sprints
import com.crackling.infrastructure.database.tables.Tasks
import org.jetbrains.exposed.v1.dao.IntEntityClass

class SprintEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<SprintEntity>(Sprints)

    var startDate by Sprints.startDate
    var endDate by Sprints.endDate
    var team by TeamEntity referencedOn Sprints.team
    val tasks by TaskEntity optionalReferrersOn Tasks.onSprint
}