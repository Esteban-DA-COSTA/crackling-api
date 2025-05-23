package com.crackling.api.resources

import com.crackling.api.hateoas.HateoasLink
import kotlinx.serialization.Serializable

@Serializable
@Suppress("unused")
enum class HttpVerb {
    GET,
    POST,
    PUT,
    DELETE,
    HEAD,
    OPTIONS,
    TRACE,
    PATCH,
    CONNECT,
    ;

    infix fun on(url: String): HateoasLink {
        return HateoasLink(this, url)
    }
}