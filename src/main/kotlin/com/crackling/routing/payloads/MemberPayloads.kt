package com.crackling.routing.payloads

import kotlinx.serialization.Serializable

/**
 * Payload sent by clients to change a member role.
 * 
 * @property role the new member role.
 * 
 * @see com.crackling.routing.configureMemberRouting
 * @see com.crackling.controllers.MemberController.changeMemberRole
 */
@Serializable
data class MemberRolePayload(val role: String)

/**
 * Payload sent by clients to add a member to a team.
 *
 * > team id is found from url.
 * 
 * @property email the email(**id**) of the member.
 * @property role the role of the member inside the team.
 * 
 * @see com.crackling.routing.configureMemberRouting
 * @see com.crackling.controllers.MemberController.addMemberToTeam
 */
@Serializable
data class MemberAddPayload(val email: String, val role: String)
