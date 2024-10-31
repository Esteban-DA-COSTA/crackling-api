package com.crackling.databases.dtos

import com.crackling.resources.HateoasLinks
import kotlinx.serialization.Serializable

@Serializable
data class MemberDTO(
    private val user: UserDTO = UserDTO("", "", ""),
    val team: TeamDTO,
    val role: String,
) : HateoasDTO {
    val email: String by user::email
    val username: String by user::username
    val password: String by user::password

    override val _links: HateoasLinks = mutableMapOf()
}

@Serializable
data class ListMembersDTO(val list: List<MemberDTO> = listOf()) : HateoasDTO {
    override val _links: HateoasLinks = mutableMapOf()
}