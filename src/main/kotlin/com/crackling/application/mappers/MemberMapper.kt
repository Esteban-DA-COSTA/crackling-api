package com.crackling.application.mappers

import com.crackling.application.api.resources.*
import com.crackling.application.dtos.member.ListMembersDTO
import com.crackling.application.dtos.member.MemberDTO
import com.crackling.application.dtos.team.TeamDTO
import com.crackling.application.dtos.user.UserDTO
import com.crackling.domain.models.Member
import com.crackling.domain.models.Team
import io.ktor.server.application.*
import io.ktor.server.resources.*

context(app: Application)
fun buildMemberDto(member: Member, teamId: Int): MemberDTO {
    val resource = MemberResource.Id(
        MemberResource(TeamResource.Id(teamId = teamId)),
        member.user.email
    )
    val dto = MemberDTO(
        user = UserDTO(member.user.email, member.user.username),
        team = member.team?.let { TeamDTO(it.id, it.name, it.description) },
        role = member.role
    ).apply {
        addAction("self") {
            protocol = HttpVerb.GET
            href = app.href(resource)
        }
        addAction("changeRole") {
            protocol = HttpVerb.PUT
            href = app.href(MemberRoleResource(resource))
        }
        addAction("remove") {
            protocol = HttpVerb.PUT
            href = app.href(MemberRemovalResource(resource))
        }
    }
    return dto
}

context(app: Application)
fun buildMemberListDto(members: List<Member>, teamId: Int): ListMembersDTO {
    val resource = MemberResource(TeamResource.Id(teamId = teamId))
    val list = members.map { buildMemberDto(it, teamId) }.toMutableList()
    return ListMembersDTO(list).apply {
        addAction("self") {
            protocol = HttpVerb.GET
            href = app.href(resource)
        }
        addAction("add") {
            protocol = HttpVerb.POST
            href = app.href(MemberResource.Add(resource))
        }
    }
}