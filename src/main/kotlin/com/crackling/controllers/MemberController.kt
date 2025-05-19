package com.crackling.controllers

import com.crackling.databases.dtos.ListMembersDTO
import com.crackling.databases.dtos.MemberDTO
import com.crackling.databases.entities.MemberEntity
import com.crackling.databases.tables.Members
import com.crackling.resources.*
import com.crackling.routing.payloads.MemberAddPayload
import io.ktor.server.application.*
import io.ktor.server.resources.*
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update

class MemberController(private val application: Application) {
    fun getMembersOfTeam(self: MemberResource): ListMembersDTO = transaction {
        val teamId = self.parent.teamId

        // Retrieve all members for the given team
        val list = buildList {
            MemberEntity.find { Members.team eq teamId }.forEach { member ->
                add(member.toDTO())
            }
        }

        // Member HATEOAS Links addition
        list.forEach { member ->
            val selfResource = MemberResource.Id(self, member.email)
            member.addLinks(
                "self" to HateoasLink(HttpVerb.GET, application.href(selfResource)),
            )
        }

        // List HATEOAS Links addition
        val memberList = ListMembersDTO(list).apply {
            addLinks(
                "self" to HateoasLink(HttpVerb.GET, application.href(self)),
                "addMember" to HateoasLink(HttpVerb.POST, application.href(MemberResource.Add(self)))
            )
        }
        return@transaction memberList
    }

    fun addMemberToTeam(self: MemberResource.Add, member: MemberAddPayload) = transaction {
        val teamId = self.parent.parent.teamId
        val memberId = CompositeID {
            it[Members.user] = member.email
            it[Members.team] = teamId
        }

        MemberEntity.new(memberId) {
            role = member.role
        }
    }

    fun getMemberByEmail(self: MemberResource.Id): MemberDTO = transaction {
        val email = self.email
        val teamResource = self.parent
        MemberEntity.find { Members.user eq email }.first().toDTO().apply {
            this._links.putAll(
                mapOf(
                    "self" to HateoasLink(HttpVerb.GET, application.href(self)),
                    "team" to HateoasLink(HttpVerb.GET, application.href(teamResource)),
                    "removeFromTeam" to HateoasLink(HttpVerb.DELETE, application.href(MemberRemovalResource(self))),
                    "changeRole" to HateoasLink(HttpVerb.PUT, application.href(MemberRoleResource(self)))
                )
            )
        }
    }

    fun removeMemberFromTeam(self: MemberRemovalResource) = transaction {
        val email = self.parent.email

        MemberEntity.find { Members.user eq email }.first().delete()
    }

    fun changeMemberRole(self: MemberRoleResource, newRole: String) = transaction {
        Members.update(where = { Members.user eq self.parent.email }) {
            it[role] = newRole
        }
    }

}