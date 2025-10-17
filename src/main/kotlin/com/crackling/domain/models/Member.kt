package com.crackling.domain.models

import com.crackling.infrastructure.database.entities.MemberEntity

data class Member(
    var user: User,
    var team: Team? = null,
    var role: String
) {
    companion object {
        fun fromEntity(entity: MemberEntity, config: MemberConfig = {}): Member {
            MemberConfiguration().apply(config)
            return Member(
                User.fromEntity(entity.user),
                if (MemberConfiguration().includeTeam) Team.fromEntity(entity.team) else null,
                entity.role
            )
        }
    }
    class MemberConfiguration(var includeTeam: Boolean = false)
}

typealias MemberConfig = Member.MemberConfiguration.() -> Unit