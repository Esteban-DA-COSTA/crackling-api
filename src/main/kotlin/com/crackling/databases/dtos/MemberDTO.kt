package com.crackling.databases.dtos

import com.crackling.databases.entities.MemberEntity
import com.crackling.resources.HateoasLinks
import kotlinx.serialization.Serializable

@Serializable
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

    fun member(member: MemberEntity, init: (MemberDTO.() -> Unit)): ListMembersDTO {
        list.add(member.toDTO().also { it.init() })
        return this
    }
}