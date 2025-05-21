package com.crackling.resources

import com.crackling.databases.dtos.HateoasBuilderScope
import kotlinx.serialization.Serializable

typealias HateoasLinks = MutableMap<String, HateoasLink>


@Serializable @HateoasBuilderScope
@Suppress("unused")
data class HateoasLink(var protocol: HttpVerb, var href: String) {
    
}

@Suppress("unused")
infix fun String.on(protocol: HttpVerb): HateoasLink = HateoasLink(protocol, this)