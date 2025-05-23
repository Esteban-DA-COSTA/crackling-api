package com.crackling.api.hateoas

import com.crackling.api.hateoas.builders.HateoasBuilderScope
import com.crackling.api.resources.HttpVerb
import kotlinx.serialization.Serializable

typealias HateoasLinks = MutableMap<String, HateoasLink>


@Serializable @HateoasBuilderScope
@Suppress("unused")
data class HateoasLink(var protocol: HttpVerb, var href: String) {
    
}

@Suppress("unused")
infix fun String.on(protocol: HttpVerb): HateoasLink = HateoasLink(protocol, this)