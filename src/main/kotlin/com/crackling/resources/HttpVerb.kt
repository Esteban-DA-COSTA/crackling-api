package com.crackling.resources

import kotlinx.serialization.Serializable

@Serializable
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
}