package com.crackling.domain.models

import com.crackling.infrastructure.database.entities.MemberEntity

data class Member(
    var user: User,
    var team: Team,
    var role: String
) {
    constructor(entity: MemberEntity): this(
        user = User(entity.user),
        team = Team(entity.team),
        role = entity.role
    )
}