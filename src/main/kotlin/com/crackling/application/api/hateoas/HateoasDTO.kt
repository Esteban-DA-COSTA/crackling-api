package com.crackling.application.api.hateoas

import com.crackling.application.api.hateoas.builders.HateoasBuilderScope
import com.crackling.application.api.resources.HttpVerb
import io.ktor.server.application.Application

@HateoasBuilderScope
interface HateoasDTO {
    val _links: HateoasLinks

    /**
     * Add links to hateoasLinks
     *
     * @param link vararg HateoasLink to add
     */
    fun addAction(name: String, init: HateoasLink.() -> Unit): HateoasLink {
        val link = HateoasLink(HttpVerb.GET, "")
        link.init()
        _links += name to link
        return link
    }
}