package com.crackling.databases.entities

import com.crackling.databases.dtos.ListMembersDTO
import com.crackling.databases.dtos.ListTaskDTO
import com.crackling.databases.dtos.TeamDTO
import com.crackling.databases.tables.Members
import com.crackling.databases.tables.Tasks
import com.crackling.databases.tables.Teams
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class TeamEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TeamEntity>(Teams)

    var name by Teams.name
    var description by Teams.description
    val members by MemberEntity referrersOn Members.team
    val tasks by TaskEntity referrersOn Tasks.team

    fun toDTO(
        withMembers: Boolean = false,
        withTasks: Boolean = false
    ): TeamDTO {
        return TeamDTO(
            id.value,
            name,
            description,
            if (withMembers) ListMembersDTO(members.map { it.toDTO(withTeam = false) }) else null,
            if (withTasks) ListTaskDTO(tasks.map { it.toDTO() }) else null
        )
    }
} 
