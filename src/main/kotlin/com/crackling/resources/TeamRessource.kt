package com.crackling.resources

import io.ktor.resources.*


@Resource("/teams")
class TeamRessource(val name: String? = null) {
    
    @Resource("{id}")
    class Id(val parent: TeamRessource = TeamRessource(), val id: Int) {

        @Resource("poker")
        class Poker(val parent: Id) {

            @Resource("create")
            class Create(val parent: Poker)

            @Resource("close")
            class Close(val parent: Poker)
        }
    }

}
