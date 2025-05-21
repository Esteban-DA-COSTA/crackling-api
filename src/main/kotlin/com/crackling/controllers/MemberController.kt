package com.crackling.controllers

import com.crackling.databases.entities.MemberEntity
import com.crackling.databases.tables.Members
import com.crackling.routing.payloads.MemberAddPayload
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update

class MemberController() {

    fun addMemberToTeam(teamId: Int, member: MemberAddPayload) = transaction {
        val memberId = CompositeID {
            it[Members.user] = member.email
            it[Members.team] = teamId
        }

        MemberEntity.new(memberId) {
            role = member.role
        }
    }

    fun removeMemberFromTeam(memberMail: String) = transaction {

        MemberEntity.find { Members.user eq memberMail }.first().delete()
    }

    fun changeMemberRole(memberEmail: String, newRole: String) = transaction {
        Members.update(where = { Members.user eq memberEmail }) {
            it[role] = newRole
        }
    }

}