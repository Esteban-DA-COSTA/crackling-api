package com.crackling.infrastructure.database.tables

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.*

object Sprints: IntIdTable() {
    val startDate = date("start_date")
    val endDate = date("end_date")
    val team = reference("team", Teams, onDelete = ReferenceOption.CASCADE)
}