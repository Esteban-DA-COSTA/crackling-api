package com.crackling.resources

import io.ktor.resources.*

@Resource("members")
@Suppress("unused") 
class MemberResource(val team: TeamResource.Id) {
    @Resource("{email}")
    class Id(val members: MemberResource, val email: String) {
        @Resource("remove")
        class Remove(val member: Id)
        
        @Resource("changeRole")
        class Role(val member: Id)
    }

    @Resource("add")
    class Add(val members: MemberResource)
}

//#region ALIASES
/**
 * Alias for [MemberResource.Id.Remove]
 */
typealias MemberRemovalResource = MemberResource.Id.Remove

/**
 * Alias for [MemberResource.Id.Role]
 */
typealias MemberRoleResource = MemberResource.Id.Role
//#endregion
