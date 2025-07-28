package com.crackling.services

import com.crackling.api.routing.payloads.MemberAddPayload
import com.crackling.domain.entities.MemberEntity
import com.crackling.domain.entities.TeamEntity
import com.crackling.domain.tables.Members
import com.crackling.domain.tables.Teams
import com.crackling.infrastructure.exceptions.ResourceNotFoundException
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update

class MemberService() {

    fun addMemberToTeam(teamId: Int, member: MemberAddPayload) = transaction {
        TeamEntity.findById(teamId) ?: throw ResourceNotFoundException(teamId.toString())
        val memberId = CompositeID {
            it[Members.user] = member.email
            it[Members.team] = teamId
        }

        MemberEntity.new(memberId) {
            role = member.role
        }
    }

    fun removeMemberFromTeam(teamId: Int, memberMail: String) = transaction {
        try {
            MemberEntity.find { (Members.user eq memberMail) and (Teams.id eq teamId) }.first().delete()
        } catch (_: NoSuchElementException) {
            throw ResourceNotFoundException(memberMail)
        }
    }

    fun changeMemberRole(memberEmail: String, newRole: String) = transaction {
        Members.update(where = { Members.user eq memberEmail }) {
            it[role] = newRole
        }
    }

}