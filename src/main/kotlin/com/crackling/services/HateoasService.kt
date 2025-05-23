package com.crackling.services

import io.ktor.server.application.*
import io.ktor.server.resources.*

interface HateoasService {
    val application: Application
    
    fun href(res: Any): String = application.href(res)

}
