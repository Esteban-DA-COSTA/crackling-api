package com.crackling.infrastructure.database.entities

import com.crackling.application.dtos.member.MemberDTO
import com.crackling.infrastructure.database.tables.Members
import com.crackling.infrastructure.database.tables.Tasks
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass

class MemberEntity(id: EntityID<CompositeID>): CompositeEntity(id) {
    companion object: CompositeEntityClass<MemberEntity>(Members)
    
    var user by UserEntity.Companion referencedOn Members.user
    var team by TeamEntity.Companion referencedOn Members.team
    var role by Members.role
    val tasks by TaskEntity optionalReferrersOn Tasks.assignee
}