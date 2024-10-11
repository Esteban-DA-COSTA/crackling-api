package com.crackling.resources

import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*



@Resource("/teams")
class TeamRessource(val name: String? = null) {
    
    @Resource("{id}")
    class Id(val parent: TeamRessource = TeamRessource(), val id: Int)

}
