package com.crackling.application.api.hateoas

import com.crackling.application.api.hateoas.builders.HateoasBuilderScope

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
}