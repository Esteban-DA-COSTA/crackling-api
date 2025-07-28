package com.crackling.infrastructure.exceptions

/**
 * An exception that signals the absence of a resource by a particular identifier.
 *
 * This exception is thrown when a requested resource cannot be found, typically based on a
 * provided unique identifier.
 *
 * @param id The identifier of the resource that could not be located.
 */
class ResourceNotFoundException(val id: String): CraklingApiException() {
    override val message: String? = "Resource not found for id $id"
}