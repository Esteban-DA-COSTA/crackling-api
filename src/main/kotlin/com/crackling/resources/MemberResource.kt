package com.crackling.resources

import io.ktor.resources.Resource

@Resource("members")
class MemberResource(val parent: TeamResource.Id) {
    @Resource("{email}")
    class Id(val parent: MemberResource, val email: String) {
        @Resource("remove")
        class Remove(val parent: Id)
        
        @Resource("changeRole")
        class Role(val parent: Id)
    }
    
    @Resource("add")
    class Add(val parent: MemberResource)
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
