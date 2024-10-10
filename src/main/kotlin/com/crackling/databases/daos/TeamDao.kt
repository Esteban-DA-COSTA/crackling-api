package com.crackling.databases.daos

import com.crackling.databases.dtos.TeamDTO
import com.crackling.databases.tables.Teams
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class TeamDao(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<TeamDao>(Teams)
    var name by Teams.name
    var description by Teams.description
    
    fun toDTO() = TeamDTO(id.value, name, description)
} 
