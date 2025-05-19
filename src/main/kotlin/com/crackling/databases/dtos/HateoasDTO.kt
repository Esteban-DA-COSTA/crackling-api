package com.crackling.databases.dtos

import com.crackling.resources.HateoasLink
import com.crackling.resources.HateoasLinks
import com.crackling.resources.HttpVerb

@HateoasBuilderScope
interface HateoasDTO {
    val _links: HateoasLinks

    /**
     * Add links to hateoasLinks
     *
     * @param link vararg HateoasLink to add
     */
    fun addLinks(vararg link: Pair<String, HateoasLink>): HateoasDTO {
        _links.putAll(link)
        return this
    }
    
    fun action(name: String, init: HateoasLink.() -> Unit): HateoasLink {
        val link = HateoasLink(HttpVerb.GET, "")
        link.init()
        _links += name to link
        return link
    }
}