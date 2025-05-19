package com.crackling.resources

import io.ktor.resources.*


@Resource("/teams")
class TeamResource(val name: String? = null) {
    
    @Resource("{teamId}")
    class Id(val parent: TeamResource = TeamResource(), val teamId: Int)

}
