package com.crackling.resources

import io.ktor.resources.*

@Resource("/auth")
class AuthResource {
    @Resource("/login")
    class Login(val parent: AuthResource = AuthResource())
    
    @Resource("/register")
    class Register(val parent: AuthResource = AuthResource())
}