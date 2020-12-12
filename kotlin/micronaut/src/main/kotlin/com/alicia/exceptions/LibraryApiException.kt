package com.alicia.exceptions

import io.micronaut.http.HttpStatus
import java.util.*

open class LibraryApiException(override val message: String, val httpStatus: HttpStatus):
        Exception("${httpStatus.code}: $message")

class BookNotFoundException(isbn: String):
        LibraryApiException("Book not found: $isbn", HttpStatus.NOT_FOUND)

class AuthorNotFoundException(authorId: UUID):
        LibraryApiException("Author not found: $authorId", HttpStatus.NOT_FOUND)

class FailureToReadImportCsvException:
        LibraryApiException("Failure to read bulk import CSV", HttpStatus.INTERNAL_SERVER_ERROR)

class EmptyImportCsvException:
        LibraryApiException("Empty CSV provided for bulk upload", HttpStatus.BAD_REQUEST)

class InvalidRequestException(errors: List<String>):
        LibraryApiException("Invalid request: [${errors.joinToString()}]", HttpStatus.BAD_REQUEST)

class MemberNotFoundException:
        LibraryApiException("Member not found",  HttpStatus.BAD_REQUEST)

class GenericBadRequestException:
        LibraryApiException("Bad request", HttpStatus.BAD_REQUEST)