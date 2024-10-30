package com.crackling.databases.entities

import com.crackling.databases.dtos.TeamDTO
import com.crackling.databases.tables.Members
import com.crackling.databases.tables.Teams
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class TeamEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<TeamEntity>(Teams)
    var name by Teams.name
    var description by Teams.description
    val members by MemberEntity referrersOn Members.team
    
    fun toDTO() = TeamDTO(id.value, name, description)
} 
