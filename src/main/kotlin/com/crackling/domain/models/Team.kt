package com.crackling.domain.models

import com.crackling.infrastructure.database.entities.TeamEntity


class Team(
    val id: Int? = null,
    var name: String = "",
    var description: String = "",
    var members: MutableList<Member>? = null
) {
    companion object {
        fun fromEntity(entity: TeamEntity, config: TeamConfiguration = {}): Team {
            val teamConfig = TeamConfig().apply(config)
            return Team(
                entity.id.value,
                entity.name,
                entity.description,
                if (teamConfig.includeMembers) entity.members.map { Member.fromEntity(it) }.toMutableList() else null
            )
        }
    }

    class TeamConfig(var includeMembers: Boolean = false, var includeSprints: Boolean = false)
}

typealias TeamConfiguration = Team.TeamConfig.() -> Unit