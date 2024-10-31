package com.crackling.resources

import io.ktor.resources.Resource

@Resource("members")
class MemberRessource(val parent: TeamRessource.Id) {
    @Resource("{email}")
    class Id(val parent: MemberRessource, val email: String) {
        @Resource("remove")
        class Remove(val parent: Id)
        
        @Resource("changeRole")
        class Role(val parent: Id)
    }
    
    @Resource("add")
    class Add(val parent: MemberRessource)
}

//#region ALIASES
/**
 * Alias for [MemberRessource.Id.Remove]
 */
typealias MemberRemoval = MemberRessource.Id.Remove

/**
 * Alias for [MemberRessource.Id.Role]
 */
typealias MemberRole = MemberRessource.Id.Role
//#endregion
