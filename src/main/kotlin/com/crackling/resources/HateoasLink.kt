package com.crackling.resources

import kotlinx.serialization.Serializable

typealias HateoasLinks = MutableMap<String, HateoasLink>


@Serializable
data class HateoasLink(val protocol: HttpVerb, val href: String) {
    
}
