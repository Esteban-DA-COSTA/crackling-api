package com.crackling.infrastructure.exceptions

abstract class CraklingApiException(
    message: String? = null,
    cause: Throwable? = null
): Exception(message, cause)