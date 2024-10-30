package com.crackling.databases.entities

import com.crackling.databases.tables.Members
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

class MemberEntity(id: EntityID<CompositeID>): CompositeEntity(id) {
    companion object: CompositeEntityClass<MemberEntity>(Members)
    
    var user by UserEntity referencedOn Members.user
    var team by TeamEntity referencedOn Members.team
    var role by Members.role
}