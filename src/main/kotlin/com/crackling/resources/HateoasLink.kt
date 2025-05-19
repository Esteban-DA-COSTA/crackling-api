package com.crackling.resources

import com.crackling.databases.dtos.HateoasBuilderScope
import kotlinx.serialization.Serializable

typealias HateoasLinks = MutableMap<String, HateoasLink>


@Serializable @HateoasBuilderScope
data class HateoasLink(var protocol: HttpVerb, var href: String) {
    
}
