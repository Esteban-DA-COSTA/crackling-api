package com.crackling.controllers

import io.ktor.server.application.*
import io.ktor.server.resources.*

interface HateoasController {
    val application: Application
    
    fun href(res: Any): String = application.href(res)

}
