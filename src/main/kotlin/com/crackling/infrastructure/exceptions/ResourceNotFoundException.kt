package com.crackling.infrastructure.exceptions

class ResourceNotFoundException(val id: String): Exception() {
    override val message: String? = "Resource not found for id $id"
}