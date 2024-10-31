package com.crackling.routing

import com.crackling.controllers.MemberController
import com.crackling.resources.MemberRessource
import com.crackling.routing.payloads.MemberAddPayload
import com.crackling.routing.payloads.MemberRolePayload
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.patch
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import io.ktor.server.routing.application

fun Route.configureMemberRouting() {
    val memberController = MemberController(this.application)
    
    get<MemberRessource> {
        call.respond(memberController.getMembersOfTeam(it))
    }
    
    post<MemberRessource.Add> {
        val member = call.receive<MemberAddPayload>()
        memberController.addMemberToTeam(it, member)
        return@post call.respond(HttpStatusCode.Created, member)
    }
    
    get<MemberRessource.Id> {
        return@get call.respond(memberController.getMemberByEmail(it))
    }
    delete<MemberRessource.Id.Remove> {
        memberController.removeMemberFromTeam(it)
        return@delete call.respond(HttpStatusCode.OK)
    }
    patch<MemberRessource.Id.Role> {
        val newRole = call.receive<MemberRolePayload>().role
        memberController.changeMemberRole(it, newRole)
        return@patch call.respond(HttpStatusCode.OK)
    }
}