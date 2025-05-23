package com.crackling.domain.entities

import com.crackling.domain.models.member.MemberDTO
import com.crackling.domain.tables.Members
import com.crackling.domain.tables.Tasks
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass

class MemberEntity(id: EntityID<CompositeID>): CompositeEntity(id) {
    companion object: CompositeEntityClass<MemberEntity>(Members)
    
    var user by UserEntity.Companion referencedOn Members.user
    var team by TeamEntity.Companion referencedOn Members.team
    var role by Members.role
    val tasks by TaskEntity.Companion optionalReferrersOn Tasks.assignee
    
    fun toDTO(withTeam: Boolean = false): MemberDTO = MemberDTO(
        user.toDTO(),
        if (withTeam) team.toDTO() else null,
        role
    )
    

    operator fun invoke() = toDTO()
}