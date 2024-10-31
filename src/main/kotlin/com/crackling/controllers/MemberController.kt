package com.crackling.controllers

import com.crackling.databases.dtos.ListMembersDTO
import com.crackling.databases.dtos.MemberDTO
import com.crackling.databases.entities.MemberEntity
import com.crackling.databases.tables.Members
import com.crackling.resources.HateoasLink
import com.crackling.resources.HttpVerb
import com.crackling.resources.MemberRemoval
import com.crackling.resources.MemberRessource
import com.crackling.resources.MemberRole
import com.crackling.routing.payloads.MemberAddPayload
import io.ktor.server.application.Application
import io.ktor.server.resources.href
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class MemberController(private val application: Application) {
    fun getMembersOfTeam(self: MemberRessource): ListMembersDTO = transaction {
        val teamId = self.parent.id

        val list = buildList<MemberDTO> {
            MemberEntity.find { Members.team eq teamId }.forEach { member ->
                add(member.toDTO())
            }
        }
        

        // Member HATEOAS Links addition
        list.forEach { member ->
            val selfResource = MemberRessource.Id(self, member.email)
            member._links.putAll(
                mapOf(
                    "self" to HateoasLink(HttpVerb.GET, application.href(selfResource)),
                )
            )
        }
        
        // List HATEOAS Links addition
        val memberList = ListMembersDTO(list).apply {
            _links.putAll(
                mapOf(
                    "self" to HateoasLink(HttpVerb.GET, application.href(self)),
                    "addMember" to HateoasLink(HttpVerb.POST, application.href(MemberRessource.Add(self)))
                )
            )
        }
        return@transaction memberList
    }

    fun addMemberToTeam(self: MemberRessource.Add, member: MemberAddPayload) = transaction {
        val teamId = self.parent.parent.id
        val memberId = CompositeID {
            it[Members.user] = member.email
            it[Members.team] = teamId
        }
        
        MemberEntity.new(memberId) {
            role = member.role
        }
    }
    
    fun getMemberByEmail(self: MemberRessource.Id): MemberDTO = transaction {
        val email = self.email
        val teamResource = self.parent
            MemberEntity.find { Members.user eq email }.first().toDTO().apply { 
            this._links.putAll(
                mapOf(
                    "self" to HateoasLink(HttpVerb.GET, application.href(self)),
                    "team" to HateoasLink(HttpVerb.GET, application.href(teamResource)),
                    "removeFromTeam" to HateoasLink(HttpVerb.DELETE, application.href(MemberRemoval(self))),
                    "changeRole" to HateoasLink(HttpVerb.PUT, application.href(MemberRole(self)))
                )
            )
        }
    }
    
    fun removeMemberFromTeam(self: MemberRemoval) = transaction {
        val email = self.parent.email
        
        MemberEntity.find { Members.user eq email }.first().delete()
    }
    
    fun changeMemberRole(self: MemberRole, newRole: String) = transaction {
        Members.update(where = { Members.user eq self.parent.email }) {
            it[role] = newRole
        }
    }
    
}