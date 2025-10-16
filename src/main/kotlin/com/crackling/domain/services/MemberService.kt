package com.crackling.domain.services

import com.crackling.domain.models.Member
import com.crackling.infrastructure.database.entities.MemberEntity
import com.crackling.infrastructure.database.entities.TeamEntity
import com.crackling.infrastructure.database.tables.Members
import com.crackling.infrastructure.database.tables.Teams
import com.crackling.infrastructure.exceptions.ResourceNotFoundException
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update

class MemberService() {

    fun addMemberToTeam(teamId: Int, member: Member): Member {
        transaction {
            TeamEntity.findById(teamId) ?: throw ResourceNotFoundException(teamId.toString())
            val memberId = CompositeID {
                it[Members.user] = member.user.email
                it[Members.team] = teamId
            }

            MemberEntity.new(memberId) {
                role = member.role
            }
        }.also { return Member(it) }
    }

    fun removeMemberFromTeam(teamId: Int, memberMail: String) = transaction {
        try {
            MemberEntity.find { (Members.user eq memberMail) and (Teams.id eq teamId) }.first().delete()
        } catch (_: NoSuchElementException) {
            throw ResourceNotFoundException(memberMail)
        }
    }

    fun changeMemberRole(memberEmail: String, teamId: Int, newRole: String): Member {
        transaction {
            val memberId = CompositeID {
                it[Members.user] = memberEmail
                it[Members.team] = teamId
            }
            MemberEntity.findByIdAndUpdate(memberId) {
                it.role = newRole
            } ?: throw ResourceNotFoundException(memberEmail)
        }.also { return Member(it) }
    }

}