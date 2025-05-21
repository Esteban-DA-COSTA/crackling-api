package com.crackling.routing

import com.crackling.controllers.MemberController
import com.crackling.resources.MemberResource
import com.crackling.routing.payloads.MemberAddPayload
import com.crackling.routing.payloads.MemberRolePayload
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.patch
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import io.ktor.server.routing.application

fun Route.configureMemberRouting() {
    val memberController = MemberController(this.application)
    
    post<MemberResource.Add> {
        val member = call.receive<MemberAddPayload>()
        memberController.addMemberToTeam(it.members.team.teamId, member)
        return@post call.respond(HttpStatusCode.Created, member)
    }
    delete<MemberResource.Id.Remove> {
        memberController.removeMemberFromTeam(it.member.email)
        return@delete call.respond(HttpStatusCode.OK)
    }
    patch<MemberResource.Id.Role> {
        val newRole = call.receive<MemberRolePayload>().role
        memberController.changeMemberRole(it.member.email, newRole)
        return@patch call.respond(HttpStatusCode.OK)
    }
}