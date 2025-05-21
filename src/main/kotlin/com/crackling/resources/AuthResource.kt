package com.crackling.resources

import io.ktor.resources.*

@Resource("/auth")
@Suppress("unused")
class AuthResource {
    @Resource("/login")
    class Login(val auth: AuthResource = AuthResource())
    
    @Resource("/register")
    class Register(val auth: AuthResource = AuthResource())
}