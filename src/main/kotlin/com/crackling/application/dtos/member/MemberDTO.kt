package com.crackling.application.dtos.member

import com.crackling.application.api.hateoas.HateoasDTO
import com.crackling.application.api.hateoas.HateoasLinks
import com.crackling.application.dtos.team.TeamDTO
import com.crackling.application.dtos.user.UserDTO
import kotlinx.serialization.Serializable

@Serializable
@Suppress("unused")
data class MemberDTO(
    private val user: UserDTO = UserDTO("", "", ""),
    val team: TeamDTO? = null,
    val role: String,
) : HateoasDTO {
    val email: String by user::email
    val username: String by user::username
    val password: String by user::password

    override val _links: HateoasLinks = mutableMapOf()

}

@Serializable
data class ListMembersDTO(val list: MutableList<MemberDTO> = mutableListOf()) : HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
}