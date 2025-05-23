package com.crackling.routing

import com.crackling.resources.MemberResource
import com.crackling.routing.payloads.MemberAddPayload
import com.crackling.routing.payloads.MemberRolePayload
import com.crackling.services.MemberService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.patch
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.Route

fun Route.configureMemberRouting() {
    val memberService = MemberService()
    
    post<MemberResource.Add> {
        val member = call.receive<MemberAddPayload>()
        memberService.addMemberToTeam(it.members.team.teamId, member)
        return@post call.respond(HttpStatusCode.Created, member)
    }
    delete<MemberResource.Id.Remove> {
        memberService.removeMemberFromTeam(it.member.email)
        return@delete call.respond(HttpStatusCode.OK)
    }
    patch<MemberResource.Id.Role> {
        val newRole = call.receive<MemberRolePayload>().role
        memberService.changeMemberRole(it.member.email, newRole)
        return@patch call.respond(HttpStatusCode.OK)
    }
}