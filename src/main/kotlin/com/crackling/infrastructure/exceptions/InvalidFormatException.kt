package com.crackling.infrastructure.exceptions

/**
 * An exception that indicates an invalid format issue in the application.
 *
 * This exception is thrown when a specific format requirement is not met
 * or when an input value does not conform to an expected format.
 *
 * @param message A custom error message describing the invalid format; defaults to a predefined message.
 */
class InvalidFormatException(message: String = "A format is incorrect."): CraklingApiException(message)