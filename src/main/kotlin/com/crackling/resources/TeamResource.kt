package com.crackling.resources

import io.ktor.resources.*


@Resource("/teams")
@Suppress("unused")
class TeamResource(val name: String? = null) {
    
    @Resource("{teamId}")
    class Id(val teams: TeamResource = TeamResource(), val teamId: Int)

}
